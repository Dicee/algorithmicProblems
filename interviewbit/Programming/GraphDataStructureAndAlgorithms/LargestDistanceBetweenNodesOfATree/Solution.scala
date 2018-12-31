package interviewbit.Programming.GraphDataStructureAndAlgorithms.LargestDistanceBetweenNodesOfATree

import scala.collection.{SeqView, mutable}
import scala.collection.mutable.ArrayBuffer

// Difficulty: easy. The algorithm isn't particularly tricky although it's a bit more annoying to write it iteratively
//             than recursively. The later wouldn't work for large cases though, since it is not tail-recursive and the
//             number of nodes can be up to 40k. Sadly, I afterwards found other solutions shorter or less expensive than
//             mine (some of them using DP, some not). The DP one seems to be making assumptions about the order in which
//             the nodes are provided though, despite this order never being explicitly mentioned in the problem.

// https://www.interviewbit.com/problems/largest-distance-between-nodes-of-a-tree/
object Solution {
  def main(args: Array[String]): Unit = {
    println(solve(Array(-1, 0, 0, 0, 3))) // 3
    println(solve(Array(-1, 0, 0, 0, 3, 4))) // 4
    println(solve(Array(-1, 0, 0, 1, 1, 3, 3, 4, 6, 7))) // 6
    println(solve(Array(-1, 0, 0, 1, 1, 3, 3, 4, 6, 7, 2))) // 6
    println(solve(Array(-1, 0, 0, 1, 1, 3, 3, 4, 6, 7, 2, 10))) // 7
  }

  def solve(parentMapping: Array[Int]) = {
    val nodes = parentMapping.indices.view.map(new Tree(_)).toArray
    for ((parent, i) <- parentMapping.view.zipWithIndex) if (parent >= 0) nodes(parent) += nodes(i)
    val depths = calculateDepths(parentMapping, nodes)

    (for (node <- nodes.view) yield top2(node.children.map(child => depths(child.id))) match {
      case List(d0, d1) => d0 + d1 + 2
      case _            => depths(node.id)
    }).max
  }

  private def calculateDepths(parentMapping: Array[Int], nodes: Array[Tree]) = {
    val depths = Array.ofDim[Int](parentMapping.length)
    val stack  = mutable.ArrayStack[(Int, Int)]()
    nodes.view.filter(_.children.isEmpty).map(node => (node.id, 0)).foreach(stack.push)

    while (stack.nonEmpty) {
      val (node, depth) = stack.pop()
      depths(node) = depth

      val parent = parentMapping(node)
      if (parent >= 0 && depth + 1 > depths(parent)) stack.push((parent, depth + 1))
    }

    depths
  }

  private def top2(depths: SeqView[Int, _]): List[Int] = {
    val topDepths = new mutable.PriorityQueue[Int]()(implicitly[Ordering[Int]].reverse)
    for (depth <- depths) {
      topDepths.enqueue(depth)
      if (topDepths.size > 2) topDepths.dequeue()
    }
    topDepths.toList
  }

  private class Tree(val id: Int) {
    private val _children = ArrayBuffer[Tree]()

    def +=(child: Tree): Unit = _children += child
    def children = _children.view
  }
}
