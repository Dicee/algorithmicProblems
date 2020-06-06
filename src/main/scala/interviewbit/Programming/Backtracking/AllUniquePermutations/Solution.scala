package interviewbit.Programming.Backtracking.AllUniquePermutations

import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting

// Difficulty: easy. I've implemented this a couple of times so it didn't take me as much time as it used to. I picked
//             an iterative solution because it doesn't have the downside of failing for deep recursions. It is a bit slower than
//             with backtracking though, as its complexity is O(n.n!) rather than O(n!) (assuming the backtracking solution
//             uses persistent immutable arrays, otherwise they'd still need to copy the whole array every time), but that's
//             no big deal.

// https://www.interviewbit.com/problems/all-unique-permutations/
object Solution {
  def main(args: Array[String]): Unit = {
    println(permute(Array(2, 2, 2)).map(_.toList).toList) // List(List(2, 2, 2))
    println(permute(Array(2, 1, 2)).map(_.toList).toList) // List(List(1, 2, 2), List(2, 1, 2), List(2, 2, 1))
    println(permute(Array(3)).map(_.toList).toList)       // List(List(3))
    println(permute(Array()).map(_.toList).toList)        // List(List())
    println(permute(Array(1, 3, 2)).map(_.toList).toList) // List(List(1, 2, 3), List(1, 3, 2), List(2, 1, 3), List(2, 3, 1), List(3, 1, 2), List(3, 2, 1))
  }

  def permute(arr: Array[Int]): Array[Array[Int]]  = {
    def swap(arr: Array[Int], i: Int, j: Int) = {
      val tmp = arr(i)
      arr(i)  = arr(j)
      arr(j)  = tmp
     }

    Sorting.quickSort(arr)
    val permutations = ArrayBuffer[Array[Int]](arr.clone())

    var continue = true
    while (continue) {
      var i = arr.length - 2
      while (i >= 0 && arr(i) >= arr(i + 1)) i -= 1 // find first non reverse-ordered index

      if (i < 0) continue = false
      else {
        var j = i
        while (j + 1 < arr.length && arr(i) < arr(j + 1)) j += 1 // find smallest value higher than value to replace. Could also binary search it.
        swap(arr, i, j)

        var (k, l) = (i + 1, arr.length - 1)
        while (k < l) { swap(arr, k, l); k += 1; l -= 1 } // reverse sub-array on the right of the replaced value

        permutations += arr.clone()
      }
    }

    permutations.toArray
  }
}
