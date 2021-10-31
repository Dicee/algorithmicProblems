package hackerrank.algorithms.implementation.larrysArray

import scala.math._

// Difficulty: kind of hard. I didn't try for very long (got lazy), but I didn't find the solvability criterion based on the parity of the number of inversions on my own.
//             The argument we can make is that a 3-elements rotation can only add, or remove, 2 inversions. Therefore, removing an odd number of inversions is impossible.
//             This doesn't prove that any even number of inversions can be corrected with these permutations though...

// https://www.hackerrank.com/challenges/larrys-array/problem
object Solution {
    def larrysArray(arr: Array[Int]): String = {
      val values = new java.util.TreeSet[Int]() // NavigableSet has powerful features
      var inversions = 0

      for (i <- arr.indices) {
          inversions += values.tailSet(arr(i)).size()
          values.add(arr(i))
      }

      if (inversions % 2 == 0) "YES" else "NO"
  }
}
