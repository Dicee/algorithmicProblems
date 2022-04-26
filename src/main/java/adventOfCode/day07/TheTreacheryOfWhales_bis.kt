package adventOfCode.day07

import kotlin.math.abs

// Difficulty: easy. That's the first AoC problem so far where I applied some "smarts" to avoid blowing up memory and time complexity,
//             so it's also a funnier one to do.

// https://adventofcode.com/2021/day/6
fun main() {
    val crabs = TheTreacheryOfWhalesBis2.crabs
    val prefixSums = crabs.scan(PrefixSum2(0, 0)) { acc, (pos, count) -> acc + PrefixSum2(pos * count, count) }.drop(1)
    val minCost = prefixSums.asSequence().withIndex()
        .map { (i, prefixSum) ->
            val (pos, count) = crabs[i]
            val leftCrabs = prefixSum - PrefixSum2(pos * count, count)
            val rightCrabs = prefixSums.last() - prefixSum

            val rightCost = rightCrabs.positionsSum - rightCrabs.crabsCount * pos
            val leftCost = leftCrabs.crabsCount * pos - leftCrabs.positionsSum

            leftCost + rightCost
        }
        .minOrNull()!!

    println(minCost)
    println(TheTreacheryOfWhales.crabs.sumOf { abs(it - TheTreacheryOfWhales.crabs[TheTreacheryOfWhales.crabs.size / 2]) })
}

data class PrefixSum2(val positionsSum: Long, val crabsCount: Int) {
    override fun toString(): String = "($positionsSum, $crabsCount)"
}
operator fun PrefixSum2.minus(that: PrefixSum2) = PrefixSum2(positionsSum - that.positionsSum, crabsCount - that.crabsCount)
operator fun PrefixSum2.plus(that: PrefixSum2) = PrefixSum2(positionsSum + that.positionsSum, crabsCount + that.crabsCount)

object TheTreacheryOfWhalesBis2 {
    val crabs = TheTreacheryOfWhales::class.java.getResource("crabs.txt")!!
        .readText()
        .split(',')
        .map { it.toLong() }
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedBy { it.first }
}