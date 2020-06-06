package hackerrank.dataStructures.heap.findTheRunningMedian

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting

// Difficulty: medium. The implementation is fairly straightforward but I can't swear I would have had the idea of using
//             two heaps if the problem wasn't in the "heap" category.

// https://www.hackerrank.com/challenges/find-the-running-median/problem
object Solution {
  def main(args: Array[String]): Unit = {
    val lowestValues = new mutable.PriorityQueue[Int]
    val highestValues = new mutable.PriorityQueue[Int]()(Ordering[Int].reverse)
    var middleValue: Option[Int] = None

    for (newValue <- scala.io.Source.stdin.getLines.drop(1).map(_.toInt)) {
      val middleValues = getMiddleValues(lowestValues, middleValue, highestValues, newValue)

      for (i <- 0 until middleValues.length / 2) {
        lowestValues.enqueue(middleValues(i))
        highestValues.enqueue(middleValues(middleValues.length - i - 1))
      }
      middleValue = if (middleValues.length % 2 == 0) None else Some(middleValues(middleValues.length / 2))

      val median = middleValue match {
        case None    => (lowestValues.head + highestValues.head) / 2.0
        case Some(m) => m
      }
      println("%.1f".format(median).replace(',', '.'))
    }
  }

  // I originally had a much more concise expression for doing this (using some Scala syntactic sugar), but it was failing
  // one of the tests with a timeout I'm not entirely sure why as it seems it was doing the same amount of collection copies
  // (one array allocation, and a sort in a copied array) but that's how it is. *Sigh*
  private def getMiddleValues(lowestValues: mutable.PriorityQueue[Int], middleValue: Option[Int], highestValues: mutable.PriorityQueue[Int], newValue: Int) = {
    val buffer = ArrayBuffer(newValue)

    if (lowestValues.nonEmpty) {
      buffer += lowestValues.dequeue()
      buffer += highestValues.dequeue()
    }

    if (middleValue.isDefined) buffer += middleValue.get
    Sorting.stableSort(buffer)
  }
}
