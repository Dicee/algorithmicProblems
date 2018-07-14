package hackerrank.algorithms.dynamicProgramming.maxArraySum

import scala.collection.mutable

// Difficulty: pretty easy, but my solution is far from optimal. I used straightforward memoization which would probably have not worked for larger problems 
//             (would require bottom-up solving rather than top-bottom). I also saw other solutions afterwards involving a single loop and using only two
//             integers of memory.

// https://www.hackerrank.com/challenges/max-array-sum/problem?h_l=playlist&slugs%5B%5D%5B%5D=interview&slugs%5B%5D%5B%5D=interview-preparation-kit&slugs%5B%5D%5B%5D=dynamic-programming
object Solution {
    def maxSubsetSum(arr: Array[Int]): Long = recSol(arr, 0, arr.length, new mutable.HashMap())
    
    private def recSol(arr: Array[Int], start: Int, end: Int, memoize: mutable.Map[(Int, Int), Long]): Long = {
        if (start >= end) Int.MinValue
        else if (start + 1 >= end) Math.max(0, arr(start))
        else if (start + 2 >= end) Math.max(0, Math.max(arr(start), arr(start + 1)))
        else {
            val takeLast = memoize.getOrElseUpdate((start, end - 2), sol(arr, start, end - 2, memoize)) + arr(end - 1)
            val skipLast = memoize.getOrElseUpdate((start, end - 1), sol(arr, start, end - 1, memoize))
            Math.max(takeLast, skipLast)
        }
    }
}
