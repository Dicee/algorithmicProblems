package adventOfCode.day03

// Difficulty: easy

// https://adventofcode.com/2021/day/3
fun main() {
    val report = BinaryDiagnostic.report
    val numLines = report.size
    val oneCounts = MutableList(report[0].length) { 0 }

    for (line in report) {
        for ((i, ch) in line.withIndex()) {
            if (ch == '1') oneCounts[i]++
        }
    }
    val gammaRateBits = oneCounts.asReversed().map { if (it > numLines - it) 1 else 0 }.withIndex()

    val gammaRate = gammaRateBits.sumOf { (p, bit) -> bit shl p }
    val epsilonRate = gammaRateBits.sumOf { (p, invBit) -> (if (invBit == 0) 1 else 0) shl p }
    println(gammaRate * epsilonRate)
}

object BinaryDiagnostic {
    val report = BinaryDiagnostic::class.java.getResource("diagnostic.txt")!!
        .readText()
        .lines()
}