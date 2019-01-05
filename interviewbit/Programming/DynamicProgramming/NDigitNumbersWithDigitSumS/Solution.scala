package interviewbit.Programming.DynamicProgramming.NDigitNumbersWithDigitSumS

// Difficulty: easy, but lucky that the matrix was limited by the maximum sum for N digits. I found a solution that was
//             a lot more space-efficient, using only O(2*sum) memory using a smart decomposition using the parity of the
//             number of digits rather than the number of digits itself, reducing the number of rows to 2 instead of N.

// https://www.interviewbit.com/problems/n-digit-numbers-with-digit-sum-s-/
object Solution {
  def main(args: Array[String]): Unit = {
    println(solve(3, 18)) // 54
    println(solve(3, 4)) // 10
    println(solve(75, 22)) // 478432066
    println(solve(50, 9000)) // 0
  }

  def solve(numDigits: Int, sum: Int) = {
    // if the sum is extremely large we already know the answer and it may not even be possible to allocate the matrix,
    // so worth handling separately
    if (sum > 9 * numDigits) 0
    else {
      val dp = Array.ofDim[Int](numDigits + 1, sum + 1)
      dp(0)(0) = 1

      // the condition j - d < sum ensures we won't have a leading 0 since j <= sum and d >= 0
      for (i <- 1 to numDigits; j <- 0 to sum; d <- 0 to 9; if j - d >= 0 && j - d < sum) {
        dp(i)(j) = (dp(i)(j) + dp(i - 1)(j - d)) % 1000000007
      }
      dp(numDigits)(sum)
    }
  }
}
