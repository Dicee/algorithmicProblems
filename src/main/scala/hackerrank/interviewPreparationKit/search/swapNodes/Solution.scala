package hackerrank.interviewPreparationKit.search.swapNodes

import scala.collection.mutable.ArrayBuffer

// Difficulty: easy, no trap

// https://www.hackerrank.com/challenges/swap-nodes-algo
object Solution {
    def solution(indices: Array[Array[Int]], queries: Array[Int]): Array[Array[Int]] = {
        val nodes = (0 to indices.length).map(new Node(_)).toArray

        for ((Array(left, right), i) <- indices.zipWithIndex) {
            if (left  > 0) nodes(i + 1).left  = nodes(left )
            if (right > 0) nodes(i + 1).right = nodes(right)
        }

        queries.map(baseDepth => collectInOrder(swapNodes(nodes(1), baseDepth)))
    }

    private def swapNodes(root: Node, baseDepth: Int) = {
        def recSwap(node: Node, depth: Int): Node = {
            if (depth % baseDepth == 0) {
                val tmp    = node.left
                node.left  = node.right
                node.right = tmp
            }
            if (node.left  != null) recSwap(node.left , depth + 1)
            if (node.right != null) recSwap(node.right, depth + 1)                        

            node
        }

        recSwap(root, 1)
    }

    private def collectInOrder(root: Node) = {
        def recTraversal(node: Node, acc: ArrayBuffer[Int]): ArrayBuffer[Int] = {
            if (node.left  != null) recTraversal(node.left , acc)
            acc += node.value
            if (node.right != null) recTraversal(node.right, acc)
            acc
        }

        recTraversal(root, ArrayBuffer()).toArray
    }

    private class Node(val value: Int) {
        var left: Node = null
        var right: Node = null
    }
}
