/**
 * Difficulty: easy, marked as medium. Could be faster/cheaper without recursion but it does the job.
 *
 * https://leetcode.com/problems/powx-n
 */
fun myPow(x: Double, n: Int): Double {
    if (n < 0) return 1 / (x * myPow(x, -(n + 1)))

    if (x == 1.0) return 1.0
    if (x == -1.0) return if (n % 2 == 0) 1.0 else -1.0
    if (x == 0.0) return 0.0
    if (n == 0) return 1.0
    if (n == 1) return x

    val pow = myPow(x, n / 2)
    return pow * pow * (if (n % 2 == 0) 1.0 else x)
}
