package hackerrank.interviewPreparationKit.stringManipulation.specialPalindromeAgain

import scala.math._

// Difficulty: medium. A couple of edge cases to think about but that's about it, it is not difficult to have optimal complexity.

// https://www.hackerrank.com/challenges/special-palindrome-again/problem?h_l=interview&playlist_slugs[]=interview-preparation-kit&playlist_slugs[]=strings
object Solution {
  def main(args: Array[String]) {
    println(substrCount(5, "asasd")) // 7
    println(substrCount(7, "abcbaba")) // 10
    println(substrCount(4, "aaaa")) // 10
    println(substrCount(5, "oooohhhhhhhohhoo")) // 47
    println(substrCount(5, "oooooohooboo")) // 33
    println(substrCount(5, "oooooohooboi")) // 31
  }

  def substrCount(n: Int, original: String): Long = {
    // simulate a last character that will not match with anything in order to force the state to flush on the last index
    // of the original string
    val s = original + '_'

    var matchStart = 0
    var leftStreak = 0
    var rightStreak = 0

    var count = 0
    var i = 0

    while (i < s.length) {
      if (s(i) != s(matchStart)) {
        if (rightStreak > 0) {
          count += min(leftStreak, rightStreak) // occurrences containing elements on both sides

          // the middle element matches the current one and they are separated by only one character
          if (rightStreak == 1 && s(matchStart + leftStreak) == s(i)) count += 1

          matchStart = i - rightStreak
          leftStreak = rightStreak
          rightStreak = 0
        } else {
          // occurrences containing elements only on the left, combinations of two characters at least
          count += leftStreak * (leftStreak - 1) / 2

          if (i + 1 < s.length && s(i + 1) == s(matchStart)) {
            rightStreak = 1
            i += 1
          } else {
            leftStreak = 1
            matchStart = i
          }
          i += 1
        }
      } else {
        if (rightStreak > 0) rightStreak += 1 else leftStreak += 1
        i += 1
      }
    }

    count + original.length
  }
}
