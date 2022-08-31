package hackerrank.challenges.algoGuruGenesis

import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList

/**
 * Internal Amazon contest. Test challenge (may not remain active forever): https://www.hackerrank.com/contests/test-1657703073/challenges.
 *
 * Difficulty: hard
 */
fun main() {
    val n = readLine()!!.trim().toInt()
    val tasks = readLine()!!.trim().split(' ').map { BigInteger(it) }
    val priorities = readLine()!!.trim().split(' ').map { it.toInt() }

    val segments = TreeSet<Segment>()
    segments.add(Segment(0, n))

    val segmentSumOccurrences = TreeMap<BigInteger, Int>()
    val prefixSums = tasks.scan(BigInteger.ZERO) { acc, k -> acc + k }
    val sol = ArrayList<BigInteger>(tasks.size)

    for (p in priorities) {
        val segment = segments.floor(Segment(p, p))!!
        val (left, right) = segment.split(p)

        val segmentSum = segment.sum(prefixSums)
        val currentCount = segmentSumOccurrences.remove(segmentSum) ?: 0
        if (currentCount > 1) segmentSumOccurrences[segmentSum] = currentCount - 1

        segmentSumOccurrences.merge(left.sum(prefixSums), 1) { o, inc -> o - inc }
        segmentSumOccurrences.merge(right.sum(prefixSums), 1) { o, inc -> o - inc }

        segments -= segment
        segments += left
        segments += right

        sol += segmentSumOccurrences.lastKey()
    }

    println(sol.joinToString(separator = " "))
}

data class Segment(val start: Int, val end: Int) : Comparable<Segment> {
    val length = end - start

    fun split(i: Int) = Segment(start, i) to Segment(i + 1, end)
    fun sum(prefixSums: List<BigInteger>) = if (length <= 0) BigInteger.ZERO else prefixSums[end] - prefixSums[start]

    override fun compareTo(other: Segment): Int = start.compareTo(other.start)
}