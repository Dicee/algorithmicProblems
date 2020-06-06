package interviewbit.Programming.DynamicProgramming.EqualAveragePartition

import scala.collection.mutable
import scala.util.Sorting

// Difficulty: quite difficult, but doable. I had a memoization solution which found a solution when one existed but
//             didn't respect all of the requirements for picking one solution when many exist. These requirements make
//             the problem a lot harder as they force to make several observations:
//                 - the average of each part of the solution is equal to the average of the whole array
//                 - we can solve the problem by just picking the smallest possible left part with the appropriate average
//                 - this can be done by solving up to N knapsack variant problems

// https://www.interviewbit.com/courses/programming/topics/dynamic-programming/
object Solution {
  def main(args: Array[String]): Unit = {
    println(avgset(Array(1, 7, 15, 29, 11, 9)).map(_.toList).toList) // List(List(9, 15), List(1, 7, 11, 29))
    println(avgset(Array(2, 4, 3)).map(_.toList).toList) // List(List(3), List(2, 4))
    println(avgset(Array(2, 4)).map(_.toList).toList) // List()
    println(avgset(Array(2, 4, 3, 1, 5)).map(_.toList).toList) // List(List(3), List(1, 2, 4, 5))
    println(avgset(Array(2, 4, 3, 1, 5, 100)).map(_.toList).toList) // List()
    println(avgset(Array(47, 14, 30, 19, 30, 4, 32, 32, 15, 2, 6, 24)).map(_.toList).toList) // List(List(2, 4, 32, 47), List(6, 14, 15, 19, 24, 30, 30, 32))
  }

  def avgset(arr: Array[Int]): Array[Array[Int]] = {
    Sorting.quickSort(arr)
    val (totalSum, totalSize) = (arr.foldLeft(0L)(_ + _), arr.length)

    // it may seem expensive to store all the candidate sets in the map, but in fact immutable sets are persistent,
    // so presumably the cost of doing that is equal to the size of the largest set plus the size of a reference times
    // the size of the map. It's still more expensive (also for stack size) than using an external mutable storage but
    // it's also more Scalatic
    val memoized = mutable.HashMap[(Long, Int, Int), Set[Int]]()

    for (leftSize <- 1 until totalSize; if (totalSum * leftSize) % totalSize == 0) {
      val leftSum = totalSum * leftSize / totalSize

      def recSol(sum: Long, remaining: Int, i: Int, acc: Set[Int]): Set[Int] = {
        if (remaining == 0) if (sum * totalSize == totalSum * leftSize) acc else Set()
        else if (i == arr.length || sum + arr(i) > leftSum) Set()
        else memoized.getOrElseUpdate((sum, remaining, i), {
          val candidate = recSol(sum + arr(i), remaining - 1, i + 1,acc + i)
          if (candidate.nonEmpty) candidate else recSol(sum, remaining, i + 1, acc)
        })
      }

      val sol = recSol(0, leftSize, 0, Set())
      if (sol.nonEmpty) return Array(
        arr.indices.view.filter(sol.contains).map(arr).toArray,
        arr.indices.view.filterNot(sol.contains).map(arr).toArray
      )
    }

    Array()
  }
}
