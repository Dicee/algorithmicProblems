package hackerrank.interviewPreparationKit.arrays.leftRotation

import scala.annotation.tailrec

// Difficulty: easy

// https://www.hackerrank.com/challenges/ctci-array-left-rotation/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=arrays
object Solution {
    def rotLeft(arr: Array[Int], numRotations: Int): Array[Int] = {
        val n      = arr.length
        val result = Array.ofDim[Int](n)
        
        @tailrec
        def displaceLeft(index: Int): Array[Int] = 
            if (numRotations == 0 || index >= n) result
            else {
                val newIndex     = (n + index - numRotations) % n
                result(newIndex) = arr(index)
                displaceLeft(index + 1)
            }

        displaceLeft(0)
    }
}
