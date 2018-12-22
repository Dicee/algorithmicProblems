package interviewbit.Programming.DynamicProgramming.RepeatingSubSequence

import scala.math._

// Difficulty: easy. Just a modified version of the longest common subsequence.

// https://www.interviewbit.com/problems/repeating-subsequence/
object Solution {
  def main(args: Array[String]): Unit = {
    println(anytwo("aaa")) // 1
    println(anytwo("aaxa")) // 1
    println(anytwo("axbyazb")) // 1
    println(anytwo("axbyaz")) // 0
  }

  def anytwo(s: String): Int  = {
    val dp = Array.ofDim[Int](s.length + 1, s.length + 1)

    for (i <- s.indices; j <- s.indices) {
      dp(i + 1)(j + 1) =
        if (i != j && s(i) == s(j)) 1 + dp(i)(j)
        else                        max(dp(i)(j + 1), dp(i + 1)(j))

      if (dp(i + 1)(j + 1) >= 2) return 1
    }
    0
  }
}
