package interviewbit.Programming.Math.GridUniquePaths

// Difficulty: easy. Not too hard to get the formula. Without big integers, can be done
//             using DP but it would be quadratic rather than linear. I thought there was
//             a way to calculate binomial coefficients without overflowing if the final
//             result doesn't overflow, but I couldn't find it back.

// https://www.interviewbit.com/problems/grid-unique-paths/
object Solution {
  def main(args: Array[String]): Unit = {
    println(uniquePaths(1, 1)) // 1
    println(uniquePaths(2, 2)) // 2
    println(uniquePaths(3, 3)) // 6
    println(uniquePaths(3, 2)) // 3
    println(uniquePaths(100, 1)) // 1
  }

  def uniquePaths(a: Int, b: Int) = choose(a + b - 2, a - 1)

  private def choose(n: Int, k: Int) = {
    var (choices, nfact) = (BigInt(1), n)

    for (kfact <- 1 to k) {
      choices *= nfact
      choices /= kfact
      nfact   -= 1
    }

    choices
  }
}
