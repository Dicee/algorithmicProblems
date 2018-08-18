package hackerrank.dataStructures.queues.castleOnTheGrid

import scala.collection.mutable

// Difficulty: classic A*, easy.

// https://www.hackerrank.com/challenges/castle-on-the-grid/problem
object Solution {
    // Complete the minimumMoves function below.
    def minimumMoves(grid: Array[String], startX: Int, startY: Int, goalX: Int, goalY: Int): Int = {
        val start = Position(startX, startY, 0)
        
        // this heuristic is suitable because it always underestimates the distance of a given position to the goal. Otherwise, A* wouldn't guarantee
        // the optimiality of the solution
        val ordering: Ordering[Position] = Ordering.by(pos => (if (goalX != pos.x) 1 else 0) + (if (goalY != pos.y) 1 else 0) + pos.distance)
        val candidates = mutable.PriorityQueue(start)(ordering.reverse)
        val explored   = mutable.HashSet(start)
        
        while (candidates.nonEmpty) {
            val pos   = candidates.dequeue()
            explored += pos
            
            if (pos.x == goalX && pos.y == goalY) return pos.distance
            for (neighbour <- pos.validNeighbours(grid); if explored add neighbour) candidates.enqueue(neighbour)
        }
        
        throw new IllegalStateException("No solution")
    }
    
    private case class Position(x: Int, y: Int, distance: Int) {
        def validNeighbours(grid: Array[String]) = {
            val d = distance + 1
            (for (i <- (x - 1 to 0 by -1)          .view) yield Position(i, y, d)).takeWhile(pos => grid(pos.x)(pos.y) != 'X') ++
            (for (i <- (x + 1 until grid.length)   .view) yield Position(i, y, d)).takeWhile(pos => grid(pos.x)(pos.y) != 'X') ++
            (for (j <- (y - 1 to 0 by -1)          .view) yield Position(x, j, d)).takeWhile(pos => grid(pos.x)(pos.y) != 'X') ++
            (for (j <- (y + 1 until grid(0).length).view) yield Position(x, j, d)).takeWhile(pos => grid(pos.x)(pos.y) != 'X')
        }
    }
}
