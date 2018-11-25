package interviewbit.Programming.TwoPointers.ThreeSumZero

import scala.collection.mutable
import scala.util.Sorting

// Difficulty: medium. What I find difficult with this type of problems is that it's not easy to figure out
//             the optimal complexity upfront, so I always hesitate starting a non-linear solution and wasting
//             time in case there is one. But sometimes, like in this case, there is none (that I know of)!
//             Once you accept that O(n.log(n)) is the best you can achieve this becomes a much easier problem
//             that is a small extension of the classical problem of finding all pairs of a given sum in an array.

// https://www.interviewbit.com/problems/3-sum-zero/
object Solution {
  def main(args: Array[String]): Unit = {
    println(threeSum(Array(-1, 0, 1, 2, -1, -4)).map(_.toList).toList)
  }

  def threeSum(arr: Array[Int]): Array[Array[Int]]  = {
    Sorting.quickSort(arr)

    var triplets = mutable.HashSet[(Int, Int, Int)]()

    for (i <- arr.indices) {
      var (j, k) = (i + 1, arr.length - 1)
      while (j < k) {
        if      (arr(j) + arr(k) > -arr(i)) k -= 1
        else if (arr(j) + arr(k) < -arr(i)) j += 1
        else {
          triplets += ((arr(i), arr(j), arr(k)))
          j += 1
          k -= 1
        }
      }
    }

    triplets.view.map { case (a, b, c) => Array(a, b, c) }.toArray
  }
}