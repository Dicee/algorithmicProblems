package interviewbit.Programming.DynamicProgramming

import scala.math._

// Difficulty: medium. My solution seems a bit more complicated than other solutions I've seen afterwards but it
//             also seemed faster. Many of them are performing inefficient string operations. My implementation is
//             O(n^2), which I don't think can be improved.

// https://www.interviewbit.com/problems/palindrome-partitioning-ii/
object PalindromePartitioningII {
  def main(args: Array[String]): Unit = {
    println(minCuts("annamannbbn")) // 3
    println(minCuts("aab")) // 1
    println(minCuts("ababb")) // 1
  }

  def minCuts(s: String) = minimizeCuts(findLongestPalindromes(s))

  // finds the longest palindrome that start at any given index of the string
  private def findLongestPalindromes(s: String) = {
    // first, find all common substrings between s and its reverse
    val reverse = s.reverse
    val dp = Array.ofDim[Int](s.length + 1, s.length + 1)
    val result = Array.fill(s.length)(1)

    for (i <- 0 until s.length; j <- 0 until s.length) {
      if (s(i) == reverse(j)) {
        dp(i + 1)(j + 1) = 1 + dp(i)(j)
        // Range of the indices of a palindrome:
        // >> (i - len + 1, i               ) in original string
        // >> (n - i - 1  , n - i + len - 2 ) in reversed string
        val len = dp(i + 1)(j + 1) // length of the candidate palindrome

        // check the last index in the reversed corresponds to the same character in the original string
        if (j == s.length - 2 - i + len) {
          val start = i - len + 1
          result(start) = max(result(start), len)
        }
      }
    }

    // now we can know what is the largest palindrome debuting at any given index
    for (i <- 0 until s.length - 1) result(i + 1) = max(result(i) - 2, result(i + 1))
    result
  }

  // finds the minimum number of cuts given a set of distances representing the length of the longest
  // palindrome starting at a given index. It's similar to the classic DP problem consisting in minimizing
  // the number of jumps to reach a cell of an array which values are the maximum length of a jump from a 
  // given cell to another.
  private def minimizeCuts(distances: Array[Int]) = {
    val dp = Array.fill(distances.length)(Int.MaxValue)
    dp(0) = 0

    def updateMin(i: Int, jump: Int): Unit = {
      val j = i + jump
      if (j == dp.length) dp(j - 1) = min(dp(j - 1), dp(i))
      else if (j < dp.length) dp(j) = min(dp(j), 1 + dp(i))
    }

    for (i <- dp.indices) {
      updateMin(i, distances(i))
      updateMin(i, 1)
    }

    if (dp.last == Int.MaxValue) throw new IllegalStateException("Should always have a solution")
    dp.last
  }
}
