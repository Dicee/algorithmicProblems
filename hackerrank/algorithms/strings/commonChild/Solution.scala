import hackerrank.algorithms.strings.commonChild

// Difficulty: trivial if you already know LCS/are trained with DP. A bit disappointing that I was able to copy-paste a fragment
//             of my LCS solution (also from Hackerrank) to solve this problem.

// https://www.hackerrank.com/challenges/common-child/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=strings
object Solution {
    def commonChild(s1: String, s2: String): Int = {
       val matrix = Array.ofDim[Int](s1.length + 1, s2.length + 1)
       for (i <- 1 until matrix.length) {
            for (j <- 1 until matrix(0).length) {
                matrix(i)(j) = 
                    if (s1(i - 1) == s2(j - 1)) 1 + matrix(i - 1)(j - 1)
                    else Math.max(matrix(i)(j - 1), matrix(i - 1)(j))
            }
        }
        matrix(s1.length)(s2.length)
    }
}
