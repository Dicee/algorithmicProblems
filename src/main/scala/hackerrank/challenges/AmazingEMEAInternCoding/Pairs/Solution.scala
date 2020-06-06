package hackerrank.challenges.AmazingEMEAInternCoding.Pairs

import scala.io.Source

// Difficulty: easy, even the "clever" solution is not too complicated, and there's a staightforward naive solution

/**
 * Implementation note:
 *
 * A bit more complicated, but more efficient than the naive brute force solution, which is O(n^2) while this solution is O(n * log(n))
 */

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/pairs
object Solution {
  def main(args: Array[String]) {
    val lines         = Source.stdin.getLines
    val expectedDelta = lines.next().split(" ")(1).toInt
    val numbers       = lines.next().split(" ").map(_.toInt).sorted

    var (low, high) = (0, 1)
    var count       = 0
    while (high < numbers.length) {
      val actualDelta = numbers(high) - numbers(low)
      if (actualDelta == expectedDelta) {
        count += 1
        // numbers are all unique, so we are guaranteed that we can increment both indices without missing any pair
        low   += 1
        high  += 1
      } else if (actualDelta < expectedDelta) {
        high += 1
      } else {
        low  += 1
        if (low == high) high += 1
      }
    }

    println(count)
  }
}
