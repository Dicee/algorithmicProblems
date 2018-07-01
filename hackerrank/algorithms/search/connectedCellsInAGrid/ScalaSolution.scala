package hackerrank.algorithms.search.connectedCellsInAGrid

import scala.collection.mutable
import java.io._

// Difficulty: classic flood-fill, so not too hard.

// https://www.hackerrank.com/challenges/connected-cell-in-a-grid/problem
object ScalaSolution {
    def connectedCell(matrix: Array[Array[Int]]): Int = {
        val explored = new mutable.HashSet[Point]
        
        // in theory the product of two ints nxm can be bigger than Int.MinValue but in our case they're both lower than 10 so an int is sufficient
        var max = Int.MinValue;
        val toExplore = new mutable.Queue[Point]()
    
        (for (i <- 0 until matrix.length; j <- 0 until matrix(0).length) yield floodFill(matrix, explored, toExplore, Point(i, j))).max
    }
    
    private def floodFill(matrix: Array[Array[Int]], explored: mutable.Set[Point], toExplore: mutable.Queue[Point], origin: Point): Int = {
        if (!shouldExplore(matrix, explored, origin)) return 0

        var count = 0
        toExplore += origin
        explored.add(origin);

        while (toExplore.nonEmpty) {
            count += 1

            val point = toExplore. dequeue()
            toExplore ++= (for { 
                di <- -1 to 1; dj <- -1 to 1
                if di != 0 || dj != 0
                val pt = Point(point.i + di, point.j + dj)
                if pt.i >= 0 && pt.j >= 0 && pt.i < matrix.length && pt.j < matrix(0).length
                if shouldExplore(matrix, explored, pt)
            } yield pt)
        }
        count
    }
    
    private def shouldExplore(matrix: Array[Array[Int]], explored: mutable.Set[Point], point: Point) = matrix(point.i)(point.j) == 1 && explored.add(point)
    
    private case class Point(i: Int, j: Int)
}
