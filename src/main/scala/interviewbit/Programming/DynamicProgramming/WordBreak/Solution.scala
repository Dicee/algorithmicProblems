package interviewbit.Programming.DynamicProgramming.WordBreak

// Difficulty: fairly easy. It's pretty much like the coins problem and other similar DP classics.

// https://www.interviewbit.com/problems/word-break/
object Solution {
  def main(args: Array[String]): Unit = {
    println(wordBreak("myinterviewtrainer", Array("trainer", "my", "interview"))) // 1
    println(wordBreak("myinterviewtrainer", Array("trainers", "my", "interview"))) // 0
  }

  def wordBreak(s: String, words: Array[String]): Int  = {
    val uniqueWords = words.distinct
    val dp = Array.ofDim[Boolean](s.length + 1)
    dp(0) = true

    for (i <- 1 to s.length) {
      dp(i) = uniqueWords.exists(word => i - word.length >= 0 && dp(i - word.length) && s.substring(i - word.length, i) == word)
    }
    if (dp(s.length)) 1 else 0
  }
}
