package hackerrank.algorithms.greedy.largestPermutation

import scala.annotation.tailrec
import scala.collection.mutable

// Difficulty: easy

// https://www.hackerrank.com/challenges/largest-permutation/problem
object Solution {
    def largestPermutation(k: Int, arr: Array[Int]): Array[Int] = {
        val valueToIndex = mutable.HashMap[Int, Int]() ++= arr.view.zipWithIndex
        
        @tailrec
        def recSol(i: Int, remainingSwaps: Int): Array[Int] = 
            if (i >= arr.length || remainingSwaps <= 0) arr
            else if (arr(i) != arr.length - i) {
                swap(i, valueToIndex(arr.length - i))
                recSol(i + 1, remainingSwaps - 1)
            }
            else recSol(i + 1, remainingSwaps)   
        
        def swap(i: Int, j: Int) = { 
            val tmp = arr(i)
            arr(i)  = arr(j)
            arr(j)  = tmp
            valueToIndex ++= List(arr(i) -> i, arr(j) -> j)
        }
        
        recSol(0, k)
    }
}
