package hackerrank.interviewPreparationKit.search.tripleSum

import scala.collection.immutable.TreeSet
import scala.annotation.tailrec
import java.util.Arrays

// Difficulty: medium. Nothing particularly hard or unusual.

// https://www.hackerrank.com/challenges/triple-sum
object Solution {
    def triplets(a: Array[Int], b: Array[Int], c: Array[Int]): Long = {
        val left  = TreeSet(a: _*).toArray
        val mid   = TreeSet(b: _*).toArray
        val right = TreeSet(c: _*).toArray

        // similar semantic to java.util.NavigableSet.lower
        def lower(arr: Array[Int], value: Int) = {
            val index = Arrays.binarySearch(arr, value)
            if (index >= 0) index
            else {
                val nextLowerIndex = - index - 2
                if (nextLowerIndex >= 0) nextLowerIndex else -1
            }
        }
        
        mid.view.map(n => {
            val validLeftElements  = lower(left, n)
            val validRightElements = lower(right, n)
            if (validLeftElements < 0 || validRightElements < 0) 0
            else (validLeftElements + 1L) * (validRightElements + 1L)
        }).sum
    }
}
