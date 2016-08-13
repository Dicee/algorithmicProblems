package hackerrank.challenges.AmazingEMEAInternCoding.Kangaroo

import scala.io.Source

// Difficulty: trivial

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/kangaroo
object Solution {

  def main(args: Array[String]) {
    val Array(x1, v1, x2, v2) = Source.stdin.getLines.next().split(" ").map(_.toInt)
    if (v1 == v2) println(if (x1 == x2) "YES" else "NO")
    else {
      // trivial maths
      val intersectionTime = (x1 - x2) / (v2 - v1).toDouble
      println(if (intersectionTime >= 0 && intersectionTime % 1 == 0) "YES" else "NO")
    }
  }
}