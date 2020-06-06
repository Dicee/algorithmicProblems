package hackerrank.interviewPreparationKit.arrays.minimumSwaps2

import scala.collection.mutable
import scala.annotation.tailrec

// Difficulty: quite difficult. I didn't come up with the optimal solution on my own, I got inspired by https://www.geeksforgeeks.org/minimum-number-swaps-required-sort-array/.

// https://www.hackerrank.com/challenges/minimum-swaps-2/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=arrays
object Solution {
    def minimumSwaps(arr: Array[Int]): Int = {
        // stable sort, this is important to not overestimate the count
      val originalIndices = arr.view.zipWithIndex.sortBy(_._1).map(_._2).toArray

      val explored = new mutable.HashSet[Int]
      var swaps    = 0

      for ((index, originalIndex) <- originalIndices.view.zipWithIndex) {
          if (index != originalIndex && !explored.contains(originalIndex)) {
              var currentIndex = originalIndex
              do {
                  explored    += currentIndex
                  currentIndex = originalIndices(currentIndex)
                  swaps       += 1
              } while(currentIndex != originalIndex)
              swaps -= 1
          }
      }

      swaps
    }
}
