package interviewbit.Programming.BitManipulation.MinXorValue

import scala.annotation.tailrec

// Difficulty: medium. That's a nice problem but I had already solved something similar on Hackerrank (maximum XOR instead of minimum) which
//             helped me doing it quicker than before. This required a few adjustments though, minimizing XOR is slightly more complicated
//             than maximizing it because n ^ n is optimal in this problem and n is not always a duplicate value.
//
//             My solution is asymptotically better than the editorial currently proposed on Interviewbit (involving a sort), but as some people
//             discussed it here (https://discuss.interviewbit.com/t/who-else-solved-this-using-a-trie/6243/5), the constant factors associated
//             with sorting might be smaller than those associated to traversing 31 nodes for each insertion/lookup. However, the trie solution
//             can be optimized to minimize its maximum depth based on the largest value. In favorable cases (large array with small values), the
//             trie solution will massively outperform the sort solution. After 10 billion values with a maximum of 31 bits, the trie solution will
//             always beat the sort solution.

// https://www.interviewbit.com/problems/min-xor-value/
object Solution {
  def main(args: Array[String]): Unit = {
    println(findMinXor(Array(0, 2, 5, 7))) // 2
    println(findMinXor(Array(0, -2, -5, -7))) // 2
    println(findMinXor(Array(0, 2, 2, 5, 7))) // 0
    println(findMinXor(Array(0, 4, 7, 9))) // 3
    println(findMinXor(Array(Int.MaxValue >> 1, (Int.MaxValue >> 1) - 100, 0))) // 100
  }

  // all values are guaranteed to be positive and lower than 10^9. Could limit to the maximum bit size of the values in the array, but overkill for this problem
  private val NumBits = 30

  def findMinXor(arr: Array[Int]) = {
    val prefixTree = new BinaryPrefixTree
    for (value <- arr) prefixTree.insert(value)
    arr.view.map(prefixTree.minimumXor).min
  }

  // note: this doesn't work for signed arithmetic. The problem didn't require it and I didn't bother implementing it
  private class BinaryPrefixTree {
    private val root = new Node()

    def insert    (value: Int): Unit = root.insert    (value, 1 << NumBits)
    def minimumXor(value: Int): Int  = root.minimumXor(value, 1 << NumBits, 0, hasDiverged = false)

    private class Node {
      private val children = Array[Node](null, null)
      private var size = 0

      @tailrec
      final def insert(value: Int, mask: Int): Unit = {
        size += 1
        if (mask != 0) {
          val bit = if ((value & mask) > 0) 1 else 0
          if (children(bit) == null) children(bit) = new Node
          children(bit).insert(value, mask >> 1)
        }
      }

      final def minimumXor(value: Int, mask: Int, acc: Int, hasDiverged: Boolean): Int =
        if (mask == 0) acc
        else {
          val bit = if ((value & mask) > 0) 1 else 0
          // XOR is minimal if the left bit is the same as right bit or they're both 0
          val (optimalBit, subOptimalBit) = (bit, bit ^ 1)

          // only allow taking the same value for this bit if multiple inserted numbers went through this node. Otherwise,
          // we would always return x ^ x, which is 0
          if (children(optimalBit) != null && (hasDiverged || children(optimalBit).size > 1)) children(optimalBit).minimumXor(value, mask >> 1, acc, hasDiverged)
          else if (children(subOptimalBit) != null) children(subOptimalBit).minimumXor(value, mask >> 1, acc + mask, true)
          else throw new IllegalStateException(s"All leaves should be at depth ${NumBits}")
        }
    }
  }
}

