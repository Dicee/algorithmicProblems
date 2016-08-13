package hackerrank.challenges.AmazingEMEAInternCoding.Candies

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.Source

// Difficulty: several edge cases, too big for non tail-recursive solution. Good problem.

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/candies
object Solution {
  def main(args: Array[String]) {
    val ratings = Source.stdin.getLines.toList.tail.map(_.toInt)

    @tailrec
    def minimumCandiesToGive(ratings: List[Int], increasingStreak: Long = 1, acc: ListBuffer[Long] = ListBuffer()): List[Long] = ratings match {
      case a :: b :: q   => minimumCandiesToGive(b :: q, if (b > a) increasingStreak + 1 else 1, acc += increasingStreak)
      case a      :: Nil => (acc += increasingStreak).toList
      case           Nil => Nil
    }
    println(minimumCandiesToGive(ratings).zip(minimumCandiesToGive(ratings.reverse).reverse).map(t => Math.max(t._1, t._2)).sum)
  }
}
