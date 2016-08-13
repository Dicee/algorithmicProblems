package hackerrank.challenges.AmazingEMEAInternCoding.AlternatingCharacters

import scala.io.Source

// Difficulty: easy. No scaling problem, no edge case.

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/alternating-characters
object Solution {
    def main(args: Array[String]) {
        Source.stdin.getLines.toList.tail
              .map(_.toList)
              .map(chars => chars.tail.zip(chars)
              .count(t => t._1 == t._2))
              .foreach(println)
    }
}