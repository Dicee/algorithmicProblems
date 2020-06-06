package interviewbit.Programming.StacksAndQueues.LargestRectangleInHistogram

import scala.collection.mutable
import scala.math._

// Difficulty: easy. The first time I encountered this problem I had to think about it for some time to find the trick,
//             now it's more of a classic. I wanted to check if I could still get it right and how fast.

// https://www.interviewbit.com/problems/largest-rectangle-in-histogram/
object IterativeSolution {
  def main(args: Array[String]): Unit = {
    println(largestRectangleArea(Array(2, 1, 5, 6, 2, 3))) // 10
    println(largestRectangleArea(Array(1))) // 1
  }

  def largestRectangleArea(heights: Array[Int]): Int  = {
    val bars = mutable.ArrayStack[Bar]()

    var maxArea = 0
    for (i <- heights.indices) {
      var index = i

      while (bars.nonEmpty && heights(i) < bars.head.height) {
        val Bar(height, j) = bars.pop()
        index   = j
        maxArea = max(maxArea, (i - j) * height)
      }

      if (bars.isEmpty || bars.head.height < heights(i)) bars.push(Bar(heights(i), index))
    }

    while (bars.nonEmpty) {
      val Bar(height, j) = bars.pop()
      maxArea = max(maxArea, (heights.length - j) * height)
    }

    maxArea
  }

  private case class Bar(height: Int, index: Int)
}
