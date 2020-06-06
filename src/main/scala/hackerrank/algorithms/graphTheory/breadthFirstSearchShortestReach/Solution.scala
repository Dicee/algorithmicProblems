package hackerrank.algorithms.graphTheory.breadthFirstSearchShortestReach

import scala.collection.mutable

// Difficulty: very classic, hence easy

// https://www.hackerrank.com/challenges/bfsshortreach/problem
object Solution {
  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in)
    val nQueries = sc.nextInt()
    for (_ <- 1 to nQueries) {
        val (nNodes, nEdges) = (sc.nextInt(), sc.nextInt())
        val nodes            = (1 to nNodes).map(new Node(_)).toArray

        for (_ <- 1 to nEdges){
          val edge = Edge(nodes(sc.nextInt() - 1), nodes(sc.nextInt() - 1))
          edge.a link edge
          edge.b link edge
        }

        val start     = nodes(sc.nextInt() - 1)
        val distances = Array.fill(nNodes)(-1)
        val explored  = mutable.HashSet[Node](start)
        var border    = mutable.HashSet[Node](start)
        var dist      = 0

        while (border.nonEmpty) {
          val newBorder = mutable.HashSet[Node]()
          for (node <- border) {
            distances(node.id - 1) = dist
            for (neighbour <- node.neighbours; if explored.add(neighbour)) newBorder += neighbour
          }

          border = newBorder
          dist  += 6
        }

        println(distances.view.filterNot(_ == 0).mkString(" "))
    }
  }

  private case class Edge(a: Node, b: Node)

  private class Node(val id: Int) {
    private val edges    = mutable.HashSet[Edge]()
    def neighbours       = for (edge <- edges) yield if (edge.a.id == id) edge.b else edge.a
    def link(edge: Edge) = edges += edge
  }
}