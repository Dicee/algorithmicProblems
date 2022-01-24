package adventOfCode.day10

// Difficulty: easy, and super classic

// https://adventofcode.com/2021/day/9
fun main() {
    val parsingResults = SyntaxScoring.chunkLines.map { parseLine(it) }

    println("Part 1: ${part1(parsingResults)}")
    println("Part 2: ${part2(parsingResults)}")
}

private fun part1(parsingResults: List<ParsingResult>) =
        parsingResults.filterIsInstance<IllegalLine>().sumOf { SyntaxScoring.illegalScoring[it.closingCharacter]!! }

private fun part2(parsingResults: List<ParsingResult>): Long {
    val allScores = parsingResults.asSequence()
        .filterIsInstance<IncompleteLine>()
        .map { it.missingSuffix
            .map { ch -> SyntaxScoring.incompleteScoring[ch]!! }
            .fold(0L) { acc, score -> acc * 5 + score }
        }
        .sorted()
        .toList()

    return allScores[allScores.size / 2]
}

private fun parseLine(line: String): ParsingResult {
    val deque = ArrayDeque<Char>()
    for (ch in line) {
        if (ch in SyntaxScoring.openingChars) deque.addFirst(ch)
        else if (deque.isEmpty() || SyntaxScoring.validPairs[deque.removeFirst()] != ch) return IllegalLine(ch)
    }
    return IncompleteLine(buildString(deque.size) {
        while (deque.isNotEmpty()) append(SyntaxScoring.validPairs[deque.removeFirst()])
    })
}

interface ParsingResult
data class IllegalLine(val closingCharacter: Char): ParsingResult
data class IncompleteLine(val missingSuffix: String): ParsingResult

object SyntaxScoring {
    val chunkLines = SyntaxScoring::class.java.getResource("chunks.txt")!!.readText().lines()
    val openingChars = setOf('(', '[', '{', '<')
    val validPairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val illegalScoring = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val incompleteScoring = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
}