package hackerrank.algorithms.search.iceCreamParlor

// Difficulty: trivial. Very weak test cases.

// https://www.hackerrank.com/challenges/icecream-parlor/problem
import scala.collection.mutable

object Solution {
  def main(args: Array[String]) {
    val lines  = scala.io.Source.stdin.getLines()
    val nTrips = lines.next().toInt

    val costsWithIndex = new mutable.HashMap[Int, Int]()

    for (_ <- 1 to nTrips) {
      val budget = lines.next().toInt
      val costs  = lines.drop(1).next().split(' ').map(_.toInt).iterator

      // not super elegant (very imperative), but works
      var (found, i) = (false, 0)
      while (!found && costs.hasNext) {
        val cost              = costs.next()
        val complementaryCost = budget - cost

        costsWithIndex.get(complementaryCost) match {
          case Some(index) =>
            println(index + " " + i)
            costsWithIndex.clear()
            found = true
          case None => costsWithIndex += cost -> i
        }

        i += 1
      }
    }
  }
}
