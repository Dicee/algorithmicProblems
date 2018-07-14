package hackerrank.algorithms.dynamicProgramming.maxArraySum

import scala.collection.mutable

// Difficulty: pretty easy, but the first solution I came up with was far from optimal. I used straightforward memoization 
//             which would probably have not worked for larger problems. Bottom-up solving rather than top-bottom allows solving 
//             any size of this problem and in fact isn't more complicated to implement for this particular problem.

// https://www.hackerrank.com/challenges/max-array-sum/problem?h_l=playlist&slugs%5B%5D%5B%5D=interview&slugs%5B%5D%5B%5D=interview-preparation-kit&slugs%5B%5D%5B%5D=dynamic-programming
object Solution {
    def maxSubsetSum(arr: Array[Int]): Long = recSol(arr, arr.length, new mutable.HashMap())
    
    private def recSol(arr: Array[Int], end: Int, memoize: mutable.Map[Int, Long]): Long = {
        if (end <= 2) Math.max(0, arr.view.slice(0, end).max)
        else {
            val takeLast = memoize.getOrElseUpdate(end - 2, recSol(arr, end - 2, memoize)) + arr(end - 1)
            val skipLast = memoize.getOrElseUpdate(end - 1, recSol(arr, end - 1, memoize))
            Math.max(takeLast, skipLast)
        }
    }
}
