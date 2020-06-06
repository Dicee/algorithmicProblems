package interviewbit.Programming.StacksAndQueues.LargestRectangleInHistogram

import scala.annotation.tailrec
import scala.math._

// Difficulty: easy. The first time I encountered this problem I had to think about it for some time to find the trick,
//             now it's more of a classic. I wanted to check if I could still get it right and how fast.

// https://www.interviewbit.com/problems/largest-rectangle-in-histogram/
object FunctionalSolution {
  def main(args: Array[String]): Unit = {
    println(largestRectangleArea(Array(2, 1, 5, 6, 2, 3))) // 10
    println(largestRectangleArea(Array(1))) // 1
  }

  def largestRectangleArea(heights: Array[Int]): Int  = {
    def updateMax(maxArea: Int, bars: List[Bar], i: Int) = bars.view.map(bar => (i - bar.index) * bar.height).foldLeft(maxArea)(max)

    @tailrec
    def recSol(bars: List[Bar], previousBars: List[Bar], maxArea: Int): Int = (bars, previousBars) match {
      case (Bar(h, i) :: t, _) =>
        val (higherBars, lowerBars) = previousBars.span(_.height > h)
        val index = higherBars.view.map(_.index).foldLeft(i)(min)
        recSol(t, Bar(h, index) :: lowerBars, updateMax(maxArea, higherBars, i))

      case (Nil, _ ) => updateMax(maxArea, previousBars, heights.length)
    }

    recSol(heights.view.zipWithIndex.map(t => Bar(t._1, t._2)).toList, Nil, Int.MinValue)
  }

  private case class Bar(height: Int, index: Int)
}