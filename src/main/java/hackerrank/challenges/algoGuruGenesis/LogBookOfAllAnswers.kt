package hackerrank.challenges.algoGuruGenesis

import kotlin.math.max

/**
 * Internal Amazon contest. Test challenge (may not remain active forever): https://www.hackerrank.com/contests/test-1657703073/challenges.
 *
 * Difficulty: easy
 */
fun main() {
    val len = readLine()!!.toInt()
    if (len == 0) {
        println(0)
        return
    }

    val arr = readLine()!!.split(' ').map { it.toLong() }
    val powersOfTwo = (0..61).asSequence().map { 1L shl it }.toSet()

    var mostFrequent = 0L
    var mostFrequentCount = 0

    val counters = mutableMapOf<Long, Int>()

    for ((i, n) in arr.withIndex()) {
        if (n in powersOfTwo && i + 1 <= arr.lastIndex) {
            val next = arr[i + 1]
            if (next % 2 != 0L) {
                val count = counters.merge(next, 1) { oldValue, newValue -> oldValue + newValue} ?: 0
                if (count > mostFrequentCount) {
                    mostFrequent = next
                    mostFrequentCount = count
                } else if (count == mostFrequentCount) {
                    mostFrequent = max(mostFrequent, next)
                }
            }
        }
    }

    println(mostFrequent)
}