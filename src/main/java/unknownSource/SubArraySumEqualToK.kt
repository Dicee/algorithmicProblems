package unknownSource

import kotlin.random.Random

// maybe not the most efficient memory-wise but very concise and still linear! Basically we just solve a similar problem (find a pair of elements which sum
// equals to k, solved with a set filled from left to right), but using the prefix sums as elements since the difference of two prefix sums gives us the sum
// of an entire sub-array
private fun findSubArraySum(arr: IntArray, sum: Int): Boolean {
    val previousPrefixSums = mutableSetOf<Int>()
    val prefixSums = arr.scan(0) { acc, v -> acc + v }

    for ((i, v) in prefixSums.withIndex()) {
        if (v - sum in previousPrefixSums) return true
        previousPrefixSums += prefixSums[i]
    }
    return false
}

fun main() {
    val arr = intArrayOf(1, -4, 2, 9, 0, 0, -7, 3, 17, 3, -8, -9, 5)

    val prefixSums = arr.scan(0) { acc, v -> acc + v }
    val allSubArraySums = prefixSums.asSequence().withIndex()
        .flatMap { (i, l) -> prefixSums.asSequence().drop(i + 1).map { r -> l to r } }
        .map { (l, r) -> r - l }
        .toSet()

    val invalidSums = generateUniqueInvalidSums(allSubArraySums, 20)

    for (sum in allSubArraySums) assert(findSubArraySum(arr, sum))
    for (sum in invalidSums) assert(!findSubArraySum(arr, sum))
}

private fun generateUniqueInvalidSums(validSums: Set<Int>, size: Int): Sequence<Int> {
    val (minSum, maxSum) = validSums.minOrNull()!! to validSums.maxOrNull()!!
    return sequence {
        val cache = mutableSetOf<Int>()
        while (true) {
            val sum = Random.nextInt(minSum / 2, maxSum * 2)
            if (sum !in validSums && sum !in cache) {
                cache += sum
                yield(sum)
            }
        }
    }.filterNot(validSums::contains).take(size)
}
