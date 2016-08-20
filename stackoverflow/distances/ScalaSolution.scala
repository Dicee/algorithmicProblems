package stackoverflow.distances

import scala.collection.immutable.HashSet

/**
 * Answer to http://stackoverflow.com/questions/39049189/an-efficient-way-to-get-and-store-the-shortest-paths/39049397#39049397
 */
object ScalaSolution {
  sealed abstract  class Cell
  object Free    extends Cell
  object Blocked extends Cell

  // assuming cells is a rectangular array with non-empty columns
  def distances(cells: Array[Array[Cell]], startingPoint: (Int, Int)) = {
    // -1 will mean that the cell is unreachable from the startingPoint
    val distances = Array.fill[Int](cells.length, cells(0).length)(-1)
    distances(startingPoint._1)(startingPoint._2) = 0

    var (currentDistance, border) = (1, validNeighbours(cells, startingPoint))
    while (border.nonEmpty) {
      border.foreach { case (i, j) => distances(i)(j) = currentDistance }
      border = border.flatMap(validNeighbours(cells, _)).filter { case (i, j) => distances(i)(j) < 0 }
      currentDistance += 1
    }

    distances
  }

  private def validNeighbours(cells: Array[Array[Cell]], startingPoint: (Int, Int)) = {
    // inlining for not doing extra work in a for yield iterating over (-1, 1) x (-1, 1). If diagonals are allowed
    // then switch for using a for yield
    Set(neighbourIfValid(cells, startingPoint, ( 1,  0)),
        neighbourIfValid(cells, startingPoint, (-1,  0)),
        neighbourIfValid(cells, startingPoint, ( 0,  1)),
        neighbourIfValid(cells, startingPoint, ( 0, -1)))
      .flatten
  }

  private def neighbourIfValid(cells: Array[Array[Cell]], origin: (Int, Int), delta: (Int, Int)) = {
    val (x, y) = (origin._1 + delta._1, origin._2 + delta._2)
    if (0 <= x && 0 <= y && x < cells.length && y < cells(0).length && cells(x)(y) == Free) {
      Some(x, y)
    } else None
  }

  def main (args: Array[String]): Unit = {
    val (n, m) = (11, 5)

    val cells: Array[Array[Cell]] = Array.fill(n, m)(Free)
    cells(1)(1) = Blocked
    cells(1)(2) = Blocked
    cells(2)(1) = Blocked

    val startingPoint = (5, 2)
    println("Initial matrix:")
    printMatrix(cells)((i, j, value) => if ((i, j) == startingPoint) "S" else if (value == Free) "." else "X")

    val distancesMatrix = distances(cells, startingPoint)
    println("\nDistances from starting point:")
    printMatrix(distancesMatrix)((i, j, value) => if (value < 0) "X" else value.toString)
  }

  private def printMatrix[T](matrix: Array[Array[T]])(formatter: (Int, Int, T) => String) = {
    for (i <- 0 until matrix.length) {
      for (j <- 0 until matrix(0).length) {
        print(formatter(i, j, matrix(i)(j)) + " ")
      }
      println()
    }
  }
}