package adventOfCode.day3

// Difficulty: fairly easy. Not hard in terms of algorithm, but required me more code than other problems so far, and it was harder to write tidily
// (especially as I'm trying to learn how to write idiomatic Kotlin code)

// https://adventofcode.com/2021/day/3
fun main() {
    val report = BinaryDiagnostic.report
    val oxygenGeneratorRate = findRate(report, 0) { repr, bitIndex, majorityBitValue ->
        repr[bitIndex].digitToInt() == when (majorityBitValue) {
            MajorityBitValue.ONE, MajorityBitValue.EVEN -> 1
            MajorityBitValue.ZERO -> 0
        }
    }
    val co2ScrubberRate = findRate(report, 0) { repr, bitIndex, majorityBitValue ->
        val bit = repr[bitIndex].digitToInt()
        when (majorityBitValue) {
            MajorityBitValue.ONE -> bit !=  1
            MajorityBitValue.ZERO -> bit != 0
            MajorityBitValue.EVEN -> bit == 0
        }
    }

    println(oxygenGeneratorRate * co2ScrubberRate)
}

fun findRate(binaryRepresentations: List<String>, bitIndex: Int, rate: Rate): Int {
    val total = binaryRepresentations.size

    var onesCount = 0
    for (repr in binaryRepresentations) if (repr[bitIndex] == '1') onesCount++

    val majorityBitValue =
            if (onesCount > total - onesCount) MajorityBitValue.ONE
            else if (onesCount == total - onesCount) MajorityBitValue.EVEN
            else MajorityBitValue.ZERO

    val candidates = binaryRepresentations.filter { rate.isValid(it, bitIndex, majorityBitValue) }

    if (candidates.size == 1) return toInt(candidates.single())
    if (candidates.isEmpty()) throw NoSuchElementException()
    return findRate(candidates, bitIndex.inc(), rate)
}

fun toInt(repr: String) = repr.reversed().withIndex().sumOf { (p, bit) -> bit.digitToInt() shl p }

fun interface Rate {
    fun isValid(repr: String, bitIndex: Int, majorityBitValue: MajorityBitValue): Boolean
}

enum class MajorityBitValue {
    ONE, ZERO, EVEN
}
