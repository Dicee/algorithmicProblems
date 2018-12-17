package interviewbit.Programming.TreeDataStructure.OrderOfPeopleHeights

import scala.annotation.tailrec
import scala.util.Sorting

// Difficulty: hard. It took me a while to find how the problem could be solved greedily (i.e. if inserted from smallest
//             to largest, there's a single slot each element can fit in, so it's fine to just insert them one by one in
//             this order). Once I understood that, it also took a little bit of time to find how to do this efficiently,
//             as it requires the knowledge of how many empty slots are available on the left of any given point, which
//             I ended up implementing in logarithmic time thanks to a specialized spatial tree (similar to quadtree but
//             in one dimension and with some tweaks).
//
//             I enjoyed this problem.

// https://www.interviewbit.com/problems/order-of-people-heights/
object Solution {
  def main(args: Array[String]): Unit = {
    println(order(Array(5, 3, 2, 6, 1, 4), Array(0, 1, 2, 0, 3, 2)).toList) // List(5, 3, 2, 1, 6, 4)
  }

  def order(heights: Array[Int], inFronts: Array[Int]) = {
    val heightsWithInFronts = heights zip inFronts
    Sorting.quickSort(heightsWithInFronts)(Ordering.by(_._1))

    val tree        = new BiTree(heights.length)
    val actualOrder = Array.ofDim[Int](heights.length)

    for ((height, inFront) <- heightsWithInFronts) {
      val index = tree.findIndexWithAvailableIndicesBelow(inFront)
      tree.insert(index)
      actualOrder(index) = height
    }

    actualOrder
  }

  // some kind of specialized quadtree for a single dimension. It has built-in assumptions that only hold
  // for how I used it in this problem i.e. inserting a range of integers.
  private class BiTree(width: Int) {
    private val root = new Node(0, width)

    def insert(n: Int) = root.insert(n)

    def findIndexWithAvailableIndicesBelow(n: Int)= {
      @tailrec
      def recSol(node: Node, occupiedLeftSlotsCount: Int): Int = {
        val availableLeftSlots = node.pos - node.left.size - occupiedLeftSlotsCount

        if (availableLeftSlots == n && node.isAvailable) node.pos
        else if (availableLeftSlots > n) recSol(node.left, occupiedLeftSlotsCount)
        else recSol(node.right, node.size - node.right.size + occupiedLeftSlotsCount)
      }

      recSol(root, 0)
    }

    private class Node(private val inclusiveMin: Int, private val exclusiveMax: Int) {
      private var _size = 0

      lazy val left  = new Node(inclusiveMin, pos)
      lazy val right = new Node(pos + 1, exclusiveMax)

      def insert(n: Int): Unit = {
        if (n > pos) right.insert(n)
        else if (n < pos) left.insert(n)
        // here we assume that all insertions will be unique, so always result in an increase of the size.
        // It is correct here because we will be inserting the indices of an array, each only a single time.
        _size += 1
      }

      def pos = inclusiveMin + (exclusiveMax - inclusiveMin) / 2
      def size = _size
      def isAvailable = size - right.size == left.size
    }
  }
}
