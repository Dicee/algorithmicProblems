import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random
import java.io.File

val rootDir = File("/Users/courtino/repos/personal/algorithmicProblems/src/main/java/miscellaneous/throttler/")

for (i in 1..6) {
    val trafficOutlineConfig = TrafficOutlineConfig(
        simulationLengthSec = 900,
        baselineSegmentConfig = TrafficOutlineSegmentConfig(
            maxTps = 150,
            maxIntervalSec = 80,
            minGapSec = -20,
            maxGapSec = 20,
        ),
        burstSegmentConfig = TrafficOutlineSegmentConfig(
            maxTps = 400,
            maxIntervalSec = 20,
            minGapSec = 40,
            maxGapSec = 250,
        )
    )

    val trafficScramblingConfig = TrafficScramblingConfig(
        stepMillis = 100,
        scramblingFactor = 0.3,
    )

    val traffic = generateTrafficOutline(rootDir, trafficOutlineConfig, i)
    scrambleTraffic(rootDir, traffic, trafficScramblingConfig, i)
}

fun generateTrafficOutline(rootDir: File, config: TrafficOutlineConfig, i: Int): MutableList<Int> {
    val tpsBuckets = MutableList(config.simulationLengthSec) { 0 }

    generateTraffic(config.baselineSegmentConfig, tpsBuckets)
    generateTraffic(config.burstSegmentConfig, tpsBuckets)

    rootDir.resolve("traffic$i.tsv").printWriter().use { out ->
        out.println("time\tcalls")
        tpsBuckets.forEachIndexed { i, bucket -> out.println("$i\t${bucket}") }
    }

    return tpsBuckets
}

fun generateTraffic(config: TrafficOutlineSegmentConfig, tpsBuckets: MutableList<Int>) {
    var i = 0
    while (i < tpsBuckets.size) {
        val tps = Random.nextInt(config.maxTps)
        val seconds = Random.nextInt(config.maxIntervalSec)

        for (j in i until min(i + seconds, tpsBuckets.size)) {
            tpsBuckets[i] = tpsBuckets[i] + tps
            i++
        }

        i += Random.nextInt(config.minGapSec, config.maxGapSec)
        i = max(0, i)
    }
}

fun scrambleTraffic(rootDir: File, traffic: MutableList<Int>, config: TrafficScramblingConfig, i: Int) {
    val clock = Clock()
    val trafficScrambler = TrafficScrambler(traffic, config.stepMillis, config.scramblingFactor, clock)
    val scrambledTraffic = MutableList(traffic.size * 1000 / config.stepMillis) { 0 }

    for (j in scrambledTraffic.indices) scrambledTraffic[j] = trafficScrambler.nextNumberOfCalls()

    rootDir.resolve("scrambled-traffic$i.tsv").printWriter().use { out ->
        out.println("time\tcalls")
        scrambledTraffic.forEachIndexed { i, bucket -> out.println("${i * config.stepMillis / 1000.0}\t${bucket}") }
    }
}

class Clock {
    private var _millis: Long = 0

    val now: Long get() = _millis
    val nowSeconds: Long get() = now / 1000

    fun advance(millis: Int) {
        _millis += millis
    }
}

class TrafficScrambler(
    private val traffic: List<Int>,
    private val stepMillis: Int,
    private val scramblingFactor: Double,
    private val clock: Clock
) {
    private var i = 0
    private var callsMade = 0

    init {
        if (scramblingFactor <= 0 || scramblingFactor > 1) throw RuntimeException("Scrambling factor should be in ]0, 1] but was $scramblingFactor")
        if (stepMillis <= 0 || 1000 % stepMillis != 0) throw RuntimeException("The step should be a positive divisor of 1000 but was $stepMillis")
    }

    fun nextNumberOfCalls(): Int {
        if (i != (clock.now / 1000).toInt()) throw RuntimeException("It appears that time was modified outside of TrafficScrambler")

        val target = traffic[i]
        if ((clock.now + stepMillis) % 1000 == 0L) {
            val calls = max(target - callsMade, 0)
            i++
            callsMade = 0
            clock.advance(stepMillis)
            return calls
        }

        val baseline = target / 1000.0 * stepMillis
        val scramblingSign = if (Random.nextBoolean()) 1 else -1
        val scrambledCalls = (baseline * (1 + scramblingSign * Random.nextDouble(scramblingFactor))).roundToInt()

        callsMade += scrambledCalls
        clock.advance(stepMillis)

        return scrambledCalls
    }
}

data class TrafficOutlineConfig(
    val simulationLengthSec: Int,
    val baselineSegmentConfig: TrafficOutlineSegmentConfig,
    val burstSegmentConfig: TrafficOutlineSegmentConfig,
)

data class TrafficOutlineSegmentConfig(
    val maxTps: Int,
    val maxIntervalSec: Int,
    val minGapSec: Int,
    val maxGapSec: Int,
)

data class TrafficScramblingConfig(
    val stepMillis: Int,
    val scramblingFactor: Double,
)
