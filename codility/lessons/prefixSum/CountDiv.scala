package codility.lessons.prefixSum

/**
 * Level : painless
 */
object CountDivScala {
    object Solution {
        def solution(a: Int, b: Int, k: Int): Int = countDivisors(b, k) - countDivisors(a, k) + (if (a % k == 0) 1 else 0)
        private def countDivisors(n: Int, k: Int) = n / k + 1
    }
}