import kotlin.math.max
import kotlin.math.min

/**
 * Difficulty: easy, marked as medium. Classic DP.
 *
 * https://leetcode.com/problems/edit-distance
 */
fun minDistance(w1: String, w2: String): Int {
    if (w1.isEmpty() || w2.isEmpty()) return max(w1.length, w2.length)

    val dp = Array(w1.length + 1, { IntArray(w2.length + 1) })

    for (i in dp.indices) {
        for (j in dp[i].indices) {
            dp[i][j] = when {
                i == 0             -> j
                j == 0             -> i
                w1[i-1] == w2[j-1] -> dp[i-1][j-1]
                else               -> 1 + min(dp[i-1][j], min(dp[i][j-1], dp[i-1][j-1]))
            }
        }
    }

    return dp[w1.length][w2.length]
}

minDistance("horse", "ros") // 3
minDistance("intention", "execution") // 5
minDistance("mart", "karma") // 3
minDistance("pneumonoultramicroscopicsilicovolcanoconiosis", "ultramicroscopically") // 27
minDistance("mmicros", "micros") // 1
