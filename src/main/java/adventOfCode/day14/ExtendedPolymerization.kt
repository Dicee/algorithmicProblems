package adventOfCode.day14

import kotlin.math.max

// Difficulty: medium. I had to think more on this one to find a scalable solution, as the problem grows exponentially (or roughly so). Implementing the
//             idea correctly also took me a bit more time than for the previous AoC problems.

// https://adventofcode.com/2021/day/14
fun main() {
    val plan = ExtendedPolymerization.polymerizationPlan()
    println("Part 1: ${solution(plan, 10)}")
    println("Part 2: ${solution(plan, 40)}")
}

private fun solution(plan: PolymerizationPlan, steps: Int): Long {
    var patternCounts = countToLong(plan.initial.asSequence().windowed(2).map { (ch1, ch2) -> ch1 to ch2 })
    val charCounts = countToLong(plan.initial.asSequence())

    for (i in 1..steps) {
        // copy because all rules apply at once, we want to isolate original pattern counts from the changes we're going to make
        val newPatternCounts = patternCounts.toMutableMap()
        for (rule in plan.rules) {
            val (ch1, ch2) = rule.pattern
            val applicableCount = patternCounts[rule.pattern]

            if (applicableCount != null) {
                newPatternCounts.incrementBy(ch1 to rule.insert, applicableCount)
                newPatternCounts.incrementBy(rule.insert to ch2, applicableCount)
                newPatternCounts.incrementBy(rule.pattern, -applicableCount)
                charCounts.incrementBy(rule.insert, applicableCount)
            }
        }
        patternCounts = newPatternCounts
    }

    return charCounts.values.maxOrNull()!! - charCounts.values.minOrNull()!!
}

private fun <T> countToLong(seq: Sequence<T>) = seq.groupingBy { it }.foldTo(mutableMapOf(), 0L) { acc, _ -> acc + 1 }

fun <K> MutableMap<K, Long>.incrementBy(k: K, n: Long) {
    this[k] = max(0, (this[k] ?: 0) + n)
}

data class PolymerizationPlan(val initial: String, val rules: List<InsertionRule>)
data class InsertionRule(val pattern: Pair<Char, Char>, val insert: Char) {
    fun matches(ch1: Char, ch2: Char) = ch1 == pattern.first && ch2 == pattern.second
}

object ExtendedPolymerization {
    fun polymerizationPlan(): PolymerizationPlan {
        val (rawInitial, rawRules) = ExtendedPolymerization::class.java.getResource("pairInsertionRules.txt")!!.readText()
            .lineSequence()
            .filter { it.isNotBlank() }
            .partition { !it.contains("->") }

        val rules = rawRules.asSequence()
            .map { it.split(" -> ") }
            .map { (pattern, insert)  -> InsertionRule(pattern[0] to pattern[1], insert.single()) }
            .toList()

        return PolymerizationPlan(rawInitial.single(), rules)
    }
}