package hackerrank.challenges.algoGuruGenesis

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Internal Amazon contest. Test challenge (may not remain active forever): https://www.hackerrank.com/contests/test-1657703073/challenges.
 *
 * Difficulty: medium. I rather liked this problem, it's fairly interesting. Pretty sure my solution is longer than it can be, but it works.
 */
fun main() {
    val (N, L) = readLine()!!.trim().split(' ').map { it.toInt() }
    val sortedPackages = readLine()!!.trim().split(' ').asSequence()
        .map { it.toInt() }
        .sortedDescending()
        .toList()

    val suffixSums = MutableList(N + 1) { 0L }
    for (i in N - 1 downTo 0) {
        suffixSums[i] += sortedPackages[i] + suffixSums[i + 1]
    }

    var i = 0
    var threshold = Long.MAX_VALUE

    while (i <= N - 1 && (sortedPackages[i] >= L || sortedPackages[i] >= threshold)) {
        val n = i + 1
        threshold = L / n + (if (L % n == 0) 0L else 1L) // we want to stay as close as possible but above L for now
        i++
    }

    var j = i

    while (j <= N - 1) {
        val diff = i * threshold + (suffixSums[i] - suffixSums[j + 1]) - L
        val maxRemovable = threshold - sortedPackages[j]
        threshold -= if (i != 0) min(maxRemovable, max(0, diff / i)) else maxRemovable

        while (i < N && sortedPackages[i] >= threshold) i++
        j = max(i, j + 1)
    }

    val rightSum = if (i >= N) 0 else suffixSums[i]
    val posDiff = abs(i * threshold + rightSum - L)
    val negDiff = abs(i * (threshold - 1) + rightSum - L)

    println(if (posDiff < negDiff) threshold else threshold - 1)
}