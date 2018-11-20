package interviewbit.Programming.DynamicProgramming

// Difficulty: trivial

// https://www.interviewbit.com/problems/distinct-subsequences/
object DistinctSubsequences {
  def main(args: Array[String]): Unit = {
    println(numDistinct("rabbbit", "rabbit")) // 3
    println(numDistinct("rabit", "rabbit"))  // 0
    println(numDistinct("rrabbittt", "rabbit")) // 6
    println(numDistinct("rrqabbpitt5dt", "rabbit")) // 6
  }

  def numDistinct(source: String, target: String) = {
    val dp = Array.ofDim[Int](target.length + 1, source.length + 1)
    for (j <- 0 to source.length) dp(0)(j) = 1
    for (i <- 1 to target.length; j <- 1 to source.length) {
      dp(i)(j) =
        if (i == 0) 1 // always a single way to build the empty string
        else {
          if (target(i - 1) != source(j - 1)) dp(i)(j - 1) // skip non-matching character
          else dp(i)(j - 1) + dp(i - 1)(j - 1) // either skip or take the character
        }
    }
    dp.last.last
  }
}
