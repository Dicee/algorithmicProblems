package adventOfCode.day07

import kotlin.math.abs
import kotlin.math.min

// Difficulty: medium. For the first part, it was easy to see the median was the answer, but it was really not obvious to me that the mean
//             was the solution for the second part, and I didn't have better ideas than a quadratic brute-force solution at first.

// https://adventofcode.com/2021/day/7
fun main() {
    val crabs = TheTreacheryOfWhales.crabs

    val median = crabs[crabs.size / 2]
    println("Part 1: ${crabs.sumOf { abs(it - median) }}")

    val avg = crabs.average()
    val costToMeanPosition = sumDistancesToMeanPosition(crabs, avg.toInt())
    val optimalCostPart2 =
            if (avg % 1 == 0.0) costToMeanPosition
            else min(
                    costToMeanPosition,
                    sumDistancesToMeanPosition(crabs, (avg + 1).toInt())
            )
    println("Part 2: $optimalCostPart2")
}

private fun sumDistancesToMeanPosition(crabs: List<Int>, mean: Int) = crabs.sumOf { sumTo(abs(it - mean)) }

private fun sumTo(n: Int) = n * (n + 1) / 2

object TheTreacheryOfWhales {
    val crabs = TheTreacheryOfWhales::class.java.getResource("crabs.txt")!!
        .readText()
        .split(',')
        .map { it.toInt() }
        .sorted()
}