package hackerrank.challenges.AmazingEMEAInternCoding.TowerBreakers

import scala.io.Source

// Difficulty: trivial with the good observation.

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/tower-breakers-1
object Solution {
  def main(args: Array[String]) {
    Source.stdin.getLines.toList.tail
      .map(_.split(" ").map(_.toInt))
      .map { case Array(n, m) => if (m == 1 || n % 2 == 0) 2 else 1 }
      .foreach(println)
  }
}