package interviewbit.Programming.Backtracking.Permutations

import scala.collection.immutable.TreeSet
import scala.collection.mutable.ArrayBuffer

// Difficulty: easy. I've implemented this a couple of times so it didn't take me as much time as it used to. I picked
//             a functional solution because I did an iterative one for https://www.interviewbit.com/problems/all-unique-permutations/ !

// https://www.interviewbit.com/problems/permutations/
object Solution {
  def main(args: Array[String]): Unit = {
    println(permute(Array(1, 3, 2)).map(_.toList).toList) // List(List(1, 2, 3), List(1, 3, 2), List(2, 1, 3), List(2, 3, 1), List(3, 1, 2), List(3, 2, 1))
  }

  def permute(arr: Array[Int]): Array[Array[Int]]  = {
    // full of mutable accumulators, not very functional, but more efficient than horrible list concatenations
    def recSol(remaining: TreeSet[Int], used: Set[Int], current: Array[Int], acc: ArrayBuffer[Array[Int]]): Unit = {
      if (remaining.isEmpty) acc.append(current.clone())
      else {
        for (v <- remaining; if !used.contains(v)) {
          current(used.size) = v
          recSol(remaining - v, used + v, current, acc)
        }
      }
    }

    val acc = ArrayBuffer[Array[Int]]()
    recSol(TreeSet[Int]() ++ arr, Set(), arr, acc)
    acc.toArray
  }
}
