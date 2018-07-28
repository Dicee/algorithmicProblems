package hackerrank.interviewPreparationKit.graphs.findTheNearestClone

import scala.annotation.tailrec
import scala.collection.mutable

// Difficulty: a simple repeated BFS was enough to pass all tests, but I keep on wondering if there was a more clever solution.

// https://www.hackerrank.com/challenges/find-the-nearest-clone/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=graphs
object Solution {
    def findShortest(graphNodes: Int, graphFrom: Array[Int], graphTo: Array[Int], colors: Array[Long], color: Int): Int = {
        val nodeToNeighbours = nodesMapping(graphFrom, graphTo)

        @tailrec
        def bfs(border: Set[Int], distance: Int, explored: Set[Int]): Int = {
            val neighbours = border.flatMap(id => nodeToNeighbours(id).view.filterNot(explored.contains))
            if (neighbours.isEmpty) -1
            else if (neighbours.exists(id => colors(id - 1) == color)) distance 
            else bfs(neighbours, distance + 1, explored ++ neighbours)
        }
        
        val matchingNodes = nodeToNeighbours.keys.filter(id => colors(id - 1) == color)
        if (matchingNodes.size <= 1) -1 else matchingNodes.map(id => bfs(Set(id), 1, Set(id))).min
    }

    // I originally implemented this using monads in a more functional style, but it proved too slow (a few seconds) and I had to switch to a mutable implementation
    private def nodesMapping(graphFrom: Array[Int], graphTo: Array[Int]) = {
        val mapping = new mutable.HashMap[Int, mutable.ArrayBuffer[Int]]
        def addEdge(from: Int, to: Int) = mapping.getOrElseUpdate(from, new mutable.ArrayBuffer()) += to
        
        for (i <- 0 until graphFrom.length) {
            addEdge(graphFrom(i), graphTo(i))
            addEdge(graphTo(i), graphFrom(i))
        }
        mapping
    }
}
