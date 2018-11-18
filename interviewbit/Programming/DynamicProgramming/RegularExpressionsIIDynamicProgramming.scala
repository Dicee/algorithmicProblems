// Difficulty: medium. Since Interviewbit is aimed at preparing interviews, I try to do these in interview-like conditions. 
//             I find this one has enough edge cases to make it fairly challenging to get exactly right in 45-60 minutes.
//             I also didn't come up with a DP solution right away, instead using simple backtracking.

// https://www.interviewbit.com/problems/regular-expression-ii/
object RegularExpressionsIIDynamicProgramming {
  def main(args: Array[String]): Unit = {
    println(isMatch("ac", "ab*c")) // 1
    println(isMatch("abcbcd", "a.*c.*d")) // 1
    println(isMatch("abbc", "ab*bbc")) // 1
    println(isMatch("efwihfioghih35i", ".*")) // 1
    println(isMatch("abc", "abcc*c*c*c*")) // 1
    println(isMatch("abc", "abcc*c*c*c")) // 0

  }

  def isMatch(toMatch: String, regexp: String): Int = {
    val (n, m) = (regexp.length, toMatch.length)
    val dp = Array.ofDim[Boolean](n + 1, m + 1)

    for (i <- -1 until n; j <- -1 until m) {
      dp(i + 1)(j + 1) =
        if (i == -1) j == -1
        else {
          val ch = regexp(i)
          if (ch == '*') dp(i)(j + 1) // jump over the quantifier (already considered)
          else {
            val canRepeat = i + 1 < regexp.length && regexp(i + 1) == '*'
            val characterMatches = j != -1 && (toMatch(j) == ch || ch == '.')

            var result = false
            result |= canRepeat && dp(i)(j + 1) // stop repeating
            if (characterMatches) {
              result |= canRepeat && dp(i + 1)(j) // consume character with wildcard
              result |= dp(i)(j) // consume simple character
            }
            result
          }
        }
    }

    if (dp(n)(m)) 1 else 0
  }
}
