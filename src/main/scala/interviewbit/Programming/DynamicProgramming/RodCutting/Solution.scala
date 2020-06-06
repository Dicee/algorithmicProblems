package interviewbit.Programming.DynamicProgramming.RodCutting

import scala.collection.mutable

// Difficulty: hard. Wasn't obvious to me that it was a DP problem, I tried a long time to make a greedy solution. Once
//             I started expressing it with DP it wasn't too difficult to find the formula, but the order in which the
//             cells of the matrix must be filled is unusual. I like this problem, it's pretty original compared to the
//             most classical matrix DP problems.

// https://www.interviewbit.com/problems/rod-cutting/
object Solution {
  def main(args: Array[String]): Unit = {
    println(rodCut(10, Array()).toList) // List()
    println(rodCut(10, Array(1)).toList) // List(1)
    println(rodCut(10, Array(1, 2)).toList) // List(2, 1)
    println(rodCut(10, Array(1, 2, 4)).toList) // List(4, 2, 1)
    println(rodCut(10, Array(1, 2, 4, 7)).toList) // List(4, 2, 1, 7)
    println(rodCut(10, Array(1, 2, 4, 7, 9)).toList) // List(4, 2, 1, 7, 9)
    println(rodCut(6, Array(1, 2, 5)).toList) // List(2, 1, 5)
    println(rodCut(6, Array(1, 3, 2)).toList) // List(3, 1, 2)
  }

  def rodCut(n: Int, arr: Array[Int]) = {
    val values = Array.ofDim[Int](arr.length + 2)
    arr.view.sorted.copyToArray(values, 1)
    values(arr.length + 1) = n

    // only need the triangular matrix above the diagonal, but allocating everything for simplicity.
    // Would be easy to optimize by allocating increasingly smaller arrays for each line and shift the indices.
    val dp = Array.fill[Cut](values.length, values.length)(Cut(-1, 0))
    for (k <- 2 until values.length; delta <- 0 until values.length - k) {
      val (i, j) = (delta, k + delta)
      val firstCut = values(j) - values(i)
      dp(i)(j) = (i + 1 until j).view
        .map(cutPoint => Cut(cutPoint, firstCut + dp(i)(cutPoint).costSoFar + dp(cutPoint)(j).costSoFar))
        .min
    }

    buildMinimalPath(values, dp)
  }

  // lengthier than recursion but more resilient because this is not tail-recursive
  private def buildMinimalPath(values: Array[Int], dp: Array[Array[Cut]]) = {
    val path = Array.ofDim[Int](values.length - 2)
    val coords = mutable.ArrayStack((0, values.length - 1))
    var k = 0

    while (coords.nonEmpty) {
      val (i, j) = coords.pop()
      val cutPoint = dp(i)(j).cutPoint

      if (cutPoint > 0) {
        // smaller cut points will be popped first to respect lexicographic order
        coords.push((cutPoint, j))
        coords.push((i, cutPoint))

        path(k) = values(cutPoint)
        k += 1
      }
    }

    path
  }

  private case class Cut(cutPoint: Int, costSoFar: Long) extends Ordered[Cut] {
    override def compare(that: Cut): Int = {
      val cmp = costSoFar compareTo that.costSoFar
      // respect lexicographic order
      if (cmp == 0) cutPoint compareTo that.cutPoint else cmp
    }
  }
}
