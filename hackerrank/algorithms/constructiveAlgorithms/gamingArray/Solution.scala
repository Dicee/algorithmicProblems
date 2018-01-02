package hackerrank.algorithms.constructiveAlgorithms.gamingArray

import scala.annotation.tailrec

// Difficulty: trivial

// https://www.hackerrank.com/challenges/an-interesting-game-1/problem
object Solution {
  def main(args: Array[String]) {
    val lines = scala.io.Source.stdin.getLines()
    val n     = lines.next().toInt

    for (_ <- 1 to n) {
      val values = lines.drop(1).next().split(" ").view.map(_.toInt).toList
      println(if (countLeftMaxima(values) % 2 == 0) "ANDY" else "BOB")
    }
  }

  @tailrec
  private def countLeftMaxima(values: List[Int], currentMax: Int = Int.MinValue, count: Int = 0): Int = values match {
    case h :: t => if (h > currentMax) countLeftMaxima(t, h, count + 1) else countLeftMaxima(t, currentMax, count)
    case Nil    => count
  }
}

