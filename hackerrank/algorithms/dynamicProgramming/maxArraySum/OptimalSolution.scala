package hackerrank.algorithms.dynamicProgramming.maxArraySum

import scala.annotation.tailrec

// Difficulty: relatively easy, the recurrence relation is not too hard to find.

// https://www.hackerrank.com/challenges/max-array-sum/problem?h_l=playlist&slugs%5B%5D%5B%5D=interview&slugs%5B%5D%5B%5D=interview-preparation-kit&slugs%5B%5D%5B%5D=dynamic-programming
object OptimalSolution {
    //        { max(0, max(arr)) if n <= 2
    // S(n) = { 
    //        { max(arr(n-1) + S(n-2), S(n-1)) otherwise
    //
    // Therefore, we can easily calculate S(n) with a bottom-up approach by only remembering
    // about the last two sub-solutions
    def maxSubsetSum(arr: Array[Int]): Long = if (!arr.exists(_ >= 0)) arr.max else recSol(arr, 0, 0, 0)

    @tailrec
    private def recSol(arr: Array[Int], end: Int, antePrevious: Int, previous: Int): Long = {
        if (end == arr.length) Math.max(antePrevious, previous)
        else recSol(arr, end + 1, previous, Math.max(previous, antePrevious + arr(end)))
    }
}
