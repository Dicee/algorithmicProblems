package interviewbit.Programming.DynamicProgramming

import scala.collection.mutable

// Difficulty: medium. I had an unfair advantage as I had already solved a similar problem in one dimension 
//             (https://www.hackerrank.com/challenges/largest-rectangle/problem). As soon as I made the connection
//             between the two, the problem became trivial to solve.

// https://www.interviewbit.com/problems/max-rectangle-in-binary-matrix/
object MaxRectangleInBinaryMatrix {
  def maximalRectangle(matrix: Array[Array[Int]]) = {
    for (i <- 1 until matrix.length; j <- matrix(0).indices; if matrix(i)(j) == 1) {
      // update maximum height above and including cell
      matrix(i)(j) += matrix(i -1)(j)
    }

    var max = 0L
    for (i <- matrix.indices) {
      max = Math.max(max, largestRectangle(matrix(i)))
    }
    max
  }

  private def largestRectangle(heights: Array[Int]): Long = {
    def area(height: Height, currentIndex: Int) = (currentIndex - height.index) * height.value

    val stack = new mutable.ArrayStack[Height]
    var max   = 0L

    for (i <- heights.indices) {
      val h     = heights(i)
      var index = i

      while (stack.nonEmpty && h < stack.head.value) {
        val building = stack.pop()
        max   = Math.max(max, area(building, i))
        index = building.index
      }

      if (stack.isEmpty || h > stack.head.value) stack.push(Height(h, index))
    }

    while (stack.nonEmpty) max = Math.max(max, area(stack.pop(), heights.length))
    max
  }

  private case class Height(value: Int, index: Int)

  def main(args: Array[String]): Unit = {
    println(maximalRectangle(Array(
      Array(1, 1, 1),
      Array(0, 1, 1),
      Array(1, 0, 1)
    ))) // 4

    println(maximalRectangle(Array(
      Array(1, 1, 1, 1, 1),
      Array(0, 1, 1, 0, 1),
      Array(1, 0, 1, 0, 1)
    ))) // 5

    println(maximalRectangle(Array(
      Array(1, 1, 1, 1, 1),
      Array(0, 1, 1, 1, 1),
      Array(1, 0, 1, 0, 1)
    ))) // 8

    println(maximalRectangle(Array(
      Array(1, 1, 1, 1, 1),
      Array(0, 1, 1, 1, 1),
      Array(1, 0, 1, 1, 1)
    ))) // 9

    println(maximalRectangle(Array(
      Array(1, 1, 1, 1, 1),
      Array(0, 1, 1, 1, 1),
      Array(1, 1, 1, 1, 1)
    ))) // 12

    println(maximalRectangle(Array(
      Array(1, 1, 1, 1, 1),
      Array(1, 1, 1, 1, 1),
      Array(1, 1, 1, 1, 1)
    ))) // 15
  }
}
