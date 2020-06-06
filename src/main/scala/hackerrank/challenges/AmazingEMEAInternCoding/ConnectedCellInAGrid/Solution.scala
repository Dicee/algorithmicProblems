package hackerrank.challenges.AmazingEMEAInternCoding.ConnectedCellInAGrid

import scala.io.Source
import scala.collection.mutable.HashSet
import scala.collection.mutable.Queue

// Difficulty: that's a classic flood-fill so it's pretty easy to get the idea for an experienced programmer, but it's an interesting
//             problem for a beginner. It requires a bit of coding, contrarily to most of the other problems of the same challenge which
//             have n-liner solutions.

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/connected-cell-in-a-grid
object Solution {
    def main(args: Array[String]) {
        val lines  = Source.stdin.getLines
        val (n, m) = (lines.next().toInt, lines.next().toInt)
        val matrix = lines.toArray.toList.map(_.split(" ").map(_ == "1").toList)  
        
        val seen = new HashSet[(Int, Int)]
        var max  = Int.MinValue
        
        def isNewExplorable(i: Int, j: Int) = matrix(i)(j) && seen.add((i, j))            
            
        for (i <- 0 until matrix.length; j <- 0 until matrix(0).length; if isNewExplorable(i, j)) {
            val toExplore   = Queue((i, j))
            // don't count (i, j) that belongs to the current region and that we just added to the set
            val initialSize = seen.size - 1
            do {
                val (row, col) = toExplore.dequeue()
                val neighbours = (for {
                    dx <- -1 to 1; dy <- -1 to 1
                    x = row + dx; y = col + dy
                    if 0 <= x && x < n && 0 <= y && y < m
                    if isNewExplorable(x, y)
                } yield (x, y)).toList
                toExplore ++= neighbours
            } while (!toExplore.isEmpty)
               
            max = Math.max(max, seen.size - initialSize) 
        }
        
        println(max)
    }
}