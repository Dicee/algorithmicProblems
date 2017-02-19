package hackerrank.algorithms.graphTheory.prismSpecialSubTree

import scala.collection.mutable

// Difficulty: easy to implement, but requires a bit of thinking to see this can be solved with a cheap greedy algorithm

// https://www.hackerrank.com/challenges/primsmstsub
object Solution {
  private class Graph(nNodes: Int) {
    private val adjacencyMatrix = Array.fill(nNodes, nNodes)(Int.MaxValue)

    def +=(from: Int, to: Int, weight: Int): Unit = {
      adjacencyMatrix(from -1)(to - 1) = Math.min(adjacencyMatrix(from -1)(to - 1), weight)
      adjacencyMatrix(to - 1)(from -1) = Math.min(adjacencyMatrix(to - 1)(from -1), weight)
    }

    def edgesFrom(from: Int): mutable.Set[CandidateEdge] = new mutable.HashSet ++=
      (for (i <- 0 until nNodes; if adjacencyMatrix(from - 1)(i) != Int.MaxValue) yield CandidateEdge(i + 1, adjacencyMatrix(from - 1)(i)))
  }

  private case class CandidateEdge(to: Int, weight: Int) extends Ordered[CandidateEdge] {
    override def compare(that: CandidateEdge): Int = that.weight.compare(weight)
  }

  private def prismWeight(graph: Graph, initialNode: Int) = {
    val candidateEdges = new mutable.PriorityQueue[CandidateEdge] ++= graph.edgesFrom(initialNode)
    val exploredNodes  = new mutable.HashSet[Int] += initialNode

    var totalWeight = 0
    while (candidateEdges.nonEmpty) {
      val minEdge = candidateEdges.dequeue()
      if (exploredNodes.add(minEdge.to)) {
        candidateEdges ++= graph.edgesFrom(minEdge.to)
        totalWeight     += minEdge.weight
      }
    }
    totalWeight
  }

  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.stdin.getLines()
    val Array(nNodes, nEdges) = lines.next().split(' ').map(_.toInt)

    val graph = new Graph(nNodes)
    for (_ <- 1 to nEdges) lines.next().split(' ').map(_.toInt) match { case Array(from, to, weight) => graph += (from, to, weight) }
    println(prismWeight(graph, lines.next().toInt))
  }
}