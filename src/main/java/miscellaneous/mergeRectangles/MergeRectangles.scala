package miscellaneous.mergeRectangles

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.math.{max, min}

/**
  * @param minRectangleArea allows controlling the number of iterations of the max rectangle in binary matrix algorithm
  *                         since it has diminishing returns
  */
class StopCondition(minRectangleArea: Int) {
  def shouldStop(largestRemainingRectangleArea: Int) = largestRemainingRectangleArea < minRectangleArea
}

object MergeRectangles {
  private type Rectangle = (Range, Range)

  /**
    * Strategy: repeatedly apply a search for the largest red rectangle the largest rectangle is sufficiently small that
    *           it presents no interest anymore. Repeat with green cells.
    */
  def mergeRectanglesSimple(grid: BinaryImageGrid, stopCondition: StopCondition) = {
    val output = grid.copy
    val boundingBox = (0 until grid.width, 0 until grid.height)

    val largestUnsetRectangles = cullLargestRectangles(toBinaryMatrix(output, boundingBox, invert = true), boundingBox, stopCondition)
    val largestSetRectangles = cullLargestRectangles(toBinaryMatrix(output, boundingBox), boundingBox, stopCondition)

    for (rectangle <- largestUnsetRectangles) output.unsetRange(rectangle._1, rectangle._2)
    for (rectangle <- largestSetRectangles) output.setRange(rectangle._1, rectangle._2)

    output
  }

 /**
  * Strategy: find the bounding box of green cells, construct between 2 and 4 (depending on the
  *           bounding box's position) large red rectangles around it, and then repeatedly apply
  *           a search for the largest red rectangle within the bounding box until the largest
  *           rectangle is sufficiently small that it presents no interest anymore.
  */
  def mergeRectanglesHybrid(grid: BinaryImageGrid, stopCondition: StopCondition) = {
    val output = grid.copy

    val boundingBox = findSetCellsBoundingBox(output)

    val mergedRectangles =
      buildSurroundingRectangles(boundingBox, output.width, output.height) :::
      cullLargestRectangles(toBinaryMatrix(output, boundingBox, invert = true), boundingBox, stopCondition)

    for (rectangle <- mergedRectangles) output.unsetRange(rectangle._1, rectangle._2)
    output
  }

  private def findSetCellsBoundingBox(grid: BinaryImageGrid) = {
    val explored = mutable.HashSet[(Int, Int)]()
    val toExplore = mutable.ArrayStack[(Int, Int)]()

    var (minI, maxI, minJ, maxJ) = (Int.MaxValue, 0, Int.MaxValue, 0)
    for (w <- 0 until grid.width; h <- 0 until grid.height) {
      if (grid.get(w, h) && explored.add((w, h))) toExplore += ((w, h))

      while (toExplore.nonEmpty) {
        val (i, j) = toExplore.pop()
        minI = min(minI, i)
        maxI = max(maxI, i)
        minJ = min(minJ, j)
        maxJ = max(maxJ, j)

        toExplore ++= (for {
          ni <- max(0, i - 1) to min(grid.width - 1, i + 1)
          nj <- max(0, j - 1) to min(grid.height - 1, j + 1)
          if grid.get(ni, nj)
          if explored.add((ni, nj))
        } yield (ni, nj))
      }
    }

    if (minI == Int.MaxValue) (0 until 0, 0 until 0) else (minI to maxI, minJ to maxJ)
  }

  private def buildSurroundingRectangles(boundingBox: Rectangle, width: Int, height: Int): List[Rectangle] = boundingBox match { case (wRange, hRange) =>
    if (wRange.isEmpty || hRange.isEmpty) return List((0 until width, 0 until height))

    val largeRectangles = mutable.ListBuffer[Rectangle]()
    var (maxI, minJ, maxJ) = (width, 0, height)

    // add rectangle above
    if (hRange.start > 0) {
      largeRectangles += ((0 until width, 0 until hRange.start))
      minJ = hRange.start
    }

    // add rectangle to the right
    if (wRange.last < width - 1) {
      maxI = wRange.last + 1
      largeRectangles += ((maxI until width, minJ until height))
    }

    // add rectangle below
    if (hRange.last < height - 1) {
      maxJ = hRange.last + 1
      largeRectangles += ((0 until maxI, maxJ until height))
    }

    // add rectangle to the left
    if (wRange.start > 0) largeRectangles += ((0 until wRange.start, minJ until maxJ))
    largeRectangles.toList
  }

  private def toBinaryMatrix(grid: BinaryImageGrid, boundingBox: Rectangle, invert: Boolean = false) = boundingBox match { case (wRange, hRange) =>
    val matrix = Array.ofDim[Byte](hRange.length, wRange.length)
    for (w <- wRange; h <- hRange) matrix(h - hRange.start)(w - wRange.start) = {
      val rawValue = if (grid.get(w, h)) 1 else 0
      (if (invert) rawValue ^ 1 else rawValue).toByte
    }
    matrix
  }

  private def cullLargestRectangles(matrix: Array[Array[Byte]], boundingBox: Rectangle, stopCondition: StopCondition) = {
    @tailrec
    def recSol(acc: ListBuffer[Rectangle] = ListBuffer()): List[Rectangle] = maxRectangleInBinaryMatrix(matrix) match {
      case (xRange, yRange) =>
        if (stopCondition.shouldStop(xRange.length * yRange.length)) acc.toList
        else {
          val wRange = yRange.offsetBy(boundingBox._1.start)
          val hRange = xRange.offsetBy(boundingBox._2.start)

          acc += ((wRange, hRange))
          for (i <- xRange; j <- yRange) matrix(i)(j) = 0
          recSol(acc)
        }
    }
    if (matrix.isEmpty || matrix(0).isEmpty) Nil else recSol()
  }

  private def maxRectangleInBinaryMatrix(matrix: Array[Array[Byte]]): Rectangle = {
    val dp = Array.ofDim[Int](matrix.length, matrix(0).length)
    // calculate height of the longest consecutive sequence of ones above a cell (inclusive)
    for (i <- dp.indices; j <- dp(0).indices; if matrix(i)(j) == 1) {
      dp(i)(j) += matrix(i)(j) + (if (i == 0) 0 else dp(i -1)(j))
    }

    dp.indices.iterator.map(i => maxRectangleInHistogram(dp(i), i)).maxBy(_._1)._2
  }

  private def maxRectangleInHistogram(heights: Array[Int], line: Int): (Long, Rectangle) = {
    def area(bar: Bar, currentIndex: Int) = (currentIndex - bar.index) * bar.height

    val stack = new mutable.ArrayStack[Bar]
    var (max, maxRectangle) = (0L, (0 until 0, 0 until 0))

    def updateMaxArea(bar: Bar, currentIndex: Int): Unit = {
      val currentArea = area(bar, currentIndex)
      if (currentArea > max) {
        max = currentArea
        maxRectangle = (line - bar.height + 1 to line, bar.index until currentIndex)
      }
    }

    for (i <- heights.indices) {
      val h = heights(i)
      var index = i

      while (stack.nonEmpty && h < stack.head.height) {
        val bar = stack.pop()
        updateMaxArea(bar, i)
        index = bar.index
      }

      if (stack.isEmpty || h > stack.head.height) stack.push(Bar(h, index))
    }

    while (stack.nonEmpty) updateMaxArea(stack.pop(), heights.length)
    (max, maxRectangle)
  }

  private case class Bar(height: Int, index: Int)

  implicit class ImplicitRange(range: Range) {
    def offsetBy(offset: Int) = range.start + offset to range.last + offset
  }
}
