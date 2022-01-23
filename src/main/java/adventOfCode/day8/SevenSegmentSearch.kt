package adventOfCode.day8

// Difficulty: easy.I quite liked it, it's original.

// https://adventofcode.com/2021/day/8
fun main() {
    println("Part1: ${part1()}")
    println("Part2: ${part2()}")
}

private fun part1(): Int {
    return SevenSegmentSearch.displays.asSequence()
        .flatMap { it.digits }
        .count { it.length in SevenSegmentSearch.uniqueSegmentsCount }
}

private fun part2(): Int {
    return SevenSegmentSearch.displays.sumOf { display ->
        val chars = ('a'..'g').toList()
        val possibilities = chars.associateWith { mutableSetOf(*chars.toTypedArray()) }

        reducePossibilitiesBySegmentsCount(display, possibilities)
        reducePossibilitiesByCharOccurrenceCount(display, possibilities)
        deduceAllSegments(possibilities)

        decode(display, possibilities)
    }
}

private fun reducePossibilitiesBySegmentsCount(display: Display, possibilities: Map<Char, MutableSet<Char>>) {
    for (pattern in display.patterns) {
        when (pattern.length) {
            2 -> retainOnly(pattern, possibilities, setOf('c', 'f'))
            3 -> retainOnly(pattern, possibilities, setOf('a', 'c', 'f'))
            4 -> retainOnly(pattern, possibilities, setOf('b', 'c', 'd', 'f'))
        }
    }
}

private fun reducePossibilitiesByCharOccurrenceCount(display: Display, possibilities: Map<Char, MutableSet<Char>>) {
    val charOccurrences = display.patterns.asSequence().flatMap { it.toList() }.groupingBy { it }.eachCount()
    for ((ch, occurrences) in charOccurrences) {
        when (occurrences) {
            4 -> retainOnly(ch, possibilities, setOf('e'))
            6 -> retainOnly(ch, possibilities, setOf('b'))
            7 -> retainOnly(ch, possibilities, setOf('d', 'g'))
            8 -> retainOnly(ch, possibilities, setOf('a', 'c'))
            9 -> retainOnly(ch, possibilities, setOf('f'))
        }
    }
}

private fun retainOnly(pattern: String, possibilities: Map<Char, MutableSet<Char>>, validPossibilities: Set<Char>) {
    pattern.forEach { retainOnly(it, possibilities, validPossibilities) }
}

private fun deduceAllSegments(possibilities: Map<Char, MutableSet<Char>>) {
    possibilities.values.forEach { options ->
        if (options.size == 1) {
            possibilities.values.asSequence().filter { it.size > 1 }.forEach { it.remove(options.single()) }
        }
    }
}

private fun decode(display: Display, possibilities: Map<Char, MutableSet<Char>>): Int {
    val powers = generateSequence(1) { 10 * it }.take(4).toList().reversed()
    return display.digits.zip(powers)
        .sumOf { (repr, p) ->
            val digit = repr
                .map { ch -> possibilities[ch]!!.single() }
                .sorted()
                .joinToString("")

            SevenSegmentSearch.digits[digit]!! * p
        }
}

private fun retainOnly(ch: Char, possibilities: Map<Char, MutableSet<Char>>, validPossibilities: Set<Char>) {
    possibilities[ch]!!.retainAll(validPossibilities)
}

data class Display(val patterns: List<String>, val digits: List<String>)

object SevenSegmentSearch {
    val digits = mapOf(
            "abcefg" to 0,
            "cf" to 1,
            "acdeg" to 2,
            "acdfg" to 3,
            "bcdf" to 4,
            "abdfg" to 5,
            "abdefg" to 6,
            "acf" to 7,
            "abcdefg" to 8,
            "abcdfg" to 9,

    )
    const val segmentsCount = 7
    val uniqueSegmentsCount = setOf(2, 3, 4, 7) // corresponds respectively to numbers 1, 7, 4, 8
    val displays = SevenSegmentSearch::class.java.getResource("displays.txt")!!
        .readText()
        .lines()
        .map {
            val (patterns, digits) = it.split(" | ").map { s -> s.split(' ') }
            Display(patterns, digits)
        }
}