import kotlin.math.exp
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import java.io.File
import java.util.ArrayDeque

val rootDir = File("/Users/courtino/repos/personal/algorithmicProblems/src/main/java/miscellaneous/throttler/")

val rateLimiterFactories = listOf(
    { clock: Clock -> TokenBucketRateLimiter(tpsThreshold = 110, burstTpsThreshold = 135, clock) },
    { clock: Clock -> SlidingWindowRateLimiter(tpsThreshold = 110, windowLengthSec = 10, clock) },
    { clock: Clock -> RampDownSlidingWindowRateLimiter(tpsThreshold = 110, burstTpsThreshold = 135, windowLengthSec = 10, clock) },
)

for (i in 1..6) {
    for (rateLimiterFactory in rateLimiterFactories) {
        val clock = Clock()
        runThrottlingSimulation(rateLimiterFactory(clock), clock, i)
    }
}

fun runThrottlingSimulation(rateLimiter: RateLimiter, clock: Clock, i: Int) {
    rootDir.resolve("${rateLimiter.javaClass.simpleName}-output-traffic$i.tsv").printWriter().use { out ->
        out.println("time\trequested_calls\tgranted_calls\tthrottles")

        val traffic = rootDir.resolve("scrambled-traffic$i.tsv").readLines()
            .drop(1)
            .map { line ->
                val parts = line.split("\t")
                parts[0].toDouble() to parts[1].toInt()
            }

        var seconds = 0
        var previousTimeSec = 0.0
        var totalRequestedCalls = 0
        var grantedCalls = 0
        var throttles = 0

        for ((timeSec, requestedCalls) in traffic) {
            clock.advance(((timeSec - previousTimeSec) * 1000).roundToLong())
            previousTimeSec = timeSec

            if (clock.now / 1000 > seconds) {
                out.println("$seconds\t$totalRequestedCalls\t$grantedCalls\t$throttles")

                totalRequestedCalls = 0
                grantedCalls = 0
                throttles = 0

                seconds++
            }

            totalRequestedCalls += requestedCalls

            val result = rateLimiter.grant(requestedCalls)
            grantedCalls += result.grantedCapacity
            throttles += result.deniedCapacity
        }
    }
}

/**
 * Implementations need not to be thread-safe, we can assume a single thread is used as the simulation is not multi-threaded
 */
interface RateLimiter {
    fun grant(requestedCapacity: Int): RateLimiterResult
}
data class RateLimiterResult(val grantedCapacity: Int, val deniedCapacity: Int)

class TokenBucketRateLimiter(tpsThreshold: Int, private val burstTpsThreshold: Int, private val clock: Clock) : RateLimiter {
    private val replenishmentRate = tpsThreshold / 1000.0

    private var tokens = burstTpsThreshold
    private var lastGrantedMs = clock.now

    override fun grant(requestedCapacity: Int): RateLimiterResult {
        val newTokens = ((clock.now - lastGrantedMs) * replenishmentRate).toInt()
        tokens = min(burstTpsThreshold, tokens + newTokens)

        val grantedCapacity = min(requestedCapacity, tokens)
        tokens -= grantedCapacity
        lastGrantedMs = clock.now

        return RateLimiterResult(grantedCapacity, requestedCapacity - grantedCapacity)
    }
}

class SlidingWindowRateLimiter(tpsThreshold: Int, windowLengthSec: Int, private val clock: Clock) : RateLimiter {
    private val segmentLengthMs = 1 // we work on 1 ms buckets for finer granularity
    private val maxBucketCount = windowLengthSec * 1000 / segmentLengthMs

    private val segments = ArrayDeque<Segment>(maxBucketCount)
    private val windowThreshold = windowLengthSec * tpsThreshold

    private var grantedWithinWindow: Int = 0

    override fun grant(requestedCapacity: Int): RateLimiterResult {
        val currentTimeBucket = clock.now / segmentLengthMs

        while (segments.isNotEmpty() && segments.first.timeBucket < currentTimeBucket - maxBucketCount) {
            val (_, callsCount) = segments.pop()
            grantedWithinWindow -= callsCount
        }

        if (segments.isEmpty() || segments.last.timeBucket < currentTimeBucket) segments.addLast(Segment(currentTimeBucket))

        val grantedCapacity = min(requestedCapacity, windowThreshold - grantedWithinWindow)
        grantedWithinWindow += grantedCapacity
        segments.last.callsCount += grantedCapacity

        return RateLimiterResult(grantedCapacity, requestedCapacity - grantedCapacity)
    }

    private data class Segment(
        val timeBucket: Long,
        var callsCount: Int = 0,
    )
}

class RampDownSlidingWindowRateLimiter(tpsThreshold: Int, burstTpsThreshold: Int, windowLengthSec: Int, private val clock: Clock) : RateLimiter {
    private val segmentLengthMs = 1 // we work on 1 ms buckets for finer granularity

    private val maxBucketCount = windowLengthSec * 1000 / segmentLengthMs
    private val segments = ArrayDeque<Segment>(maxBucketCount)

    private val windowThreshold = windowLengthSec * tpsThreshold
    private val windowBurstThreshold = windowLengthSec * burstTpsThreshold

    private var attemptedCallsWithinWindow: Int = 0
    private var grantedCallsWithinWindow: Int = 0

    override fun grant(requestedCapacity: Int): RateLimiterResult {
        val currentTimeBucket = clock.now / segmentLengthMs

        while (segments.isNotEmpty() && segments.first.timeBucket < currentTimeBucket - maxBucketCount) {
            val (_, attemptedCallsCount, grantedCallsCount) = segments.pop()
            attemptedCallsWithinWindow -= attemptedCallsCount
            grantedCallsWithinWindow -= grantedCallsCount
        }

        if (segments.isEmpty() || segments.last.timeBucket < currentTimeBucket) segments.addLast(Segment(currentTimeBucket))

        val totalRequested = attemptedCallsWithinWindow + requestedCapacity
        val maxTotalGranted = grantedCallsWithinWindow + requestedCapacity

        val grantedCapacity =
            if (maxTotalGranted <= windowThreshold) requestedCapacity
            else if (maxTotalGranted <= windowBurstThreshold) linearRampDown(requestedCapacity, totalRequested)
            else exponentialRampDown(requestedCapacity, totalRequested)

        val rateLimiterResult = RateLimiterResult(grantedCapacity, requestedCapacity - grantedCapacity)
        attemptedCallsWithinWindow += requestedCapacity
        grantedCallsWithinWindow += grantedCapacity
        segments.last.update(rateLimiterResult)

        return rateLimiterResult
    }

    private fun linearRampDown(requestedCapacity: Int, totalRequested: Int) =
        (requestedCapacity * windowThreshold.toDouble() / totalRequested).roundToInt()

    private fun exponentialRampDown(requestedCapacity: Int, totalRequested: Int) =
        (requestedCapacity * exp(windowThreshold - totalRequested.toDouble())).roundToInt()

    private data class Segment(
        val timeBucket: Long,
        var attemptedCallsCount: Int = 0,
        var grantedCallsCount: Int = 0,
    ) {
        fun update(rateLimiterResult: RateLimiterResult) {
            attemptedCallsCount += rateLimiterResult.grantedCapacity + rateLimiterResult.deniedCapacity
            grantedCallsCount += rateLimiterResult.grantedCapacity
        }
    }
}

class Clock {
    private var _millis: Long = 0

    val now: Long get() = _millis
    val nowSeconds: Long get() = now / 1000

    fun advance(millis: Long) {
        _millis += millis
    }
}
