package hackerrank.dataStructures.arrays.arrayManipulation

// Difficulty: I'll be honest, I didn't find the optimal, linear solution by myself. It's pretty ingenious but very easy to implement.
//             The best idea I could come up with was to implement an interval tree to find all intervals intersecting each of the cells
//             at the end of the m queries. I believe the complexity would have been O(n log(n + m)). The implementation would have been 
//             substantially more difficult than the linear solution.
//             Therefore, the 'Hard' tag attributed to this question by HackerRank makes sense.

// https://www.hackerrank.com/challenges/crush/problem
object Solution {
    def arrayManipulation(n: Int, queries: Array[Array[Int]]): Long = {
        val arr = Array.ofDim[Int](n)
        for (Array(low, high, k) <- queries) {
            arr(low  - 1) += k
            if (high < arr.length) arr(high) -= k
        }

        var (runningSum, max) = (0L, 0L)
        for (i <- arr) { runningSum += i; max = runningSum.max(max) }
        max
    }
}
