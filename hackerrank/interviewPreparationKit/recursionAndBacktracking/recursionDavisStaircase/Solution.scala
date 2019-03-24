package hackerrank.interviewPreparationKit.recursionAndBacktracking.recursionDavisStaircase

// Difficulty: trivial, very classic

// https://www.hackerrank.com/challenges/ctci-recursive-staircase/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking
object Solution {
  def main(args: Array[String]): Unit = {
    println(stepPerms(3)) // 4
    println(stepPerms(5)) // 13
    println(stepPerms(7)) // 44
    println(stepPerms(35)) // 1132436852
  }

  val Max = 10000000007L

  def stepPerms(n: Int): Int = {
    val dp = Array.ofDim[Long](n + 1)
    dp(0) = 1

    for (i <- dp.indices) {
      for (step <- 1 to 3; if i - step >= 0) dp(i) = (dp(i) + dp(i - step)) % Max
    }

    // the problem is wrong... it's taking modulo 10^10 + 7 instead of 10^9 + 7
    dp(n).toInt
  }
}
