package codility.lessons.dynamicProgramming

/**
 * Level : respectable
 * *******************
 * 
 * This is marked by Codility as a DP problem, then the first thing to do is to
 * understand which decomposition of the problem verifies both required properties
 * for a DP problem : sub-optimality and overlapping subproblems. This can be done
 * by establishing a recurrence formula defining the solution for a given left 
 * sub-array (going from indices 0 to m < n).
 * 
 * Let's assume we know the optimal solutions for arr(0...n - 1), arr(0...n - 2), ... , 
 * arr(0...n - 6) (last index excluded), let's call them sol(n -  1) ... sol(n - 6).  
 * 
 * sol(n) can then be expressed as sol(n) = max(arr(n - 1) + sol(n - i)) for i in [1, min(n, 6)] and n >= 1.
 * 
 * This formula clearly demonstrates the sub-optimality by expressing the optimal solution
 * as a function of the optimal solutions of some sub-problems.
 * 
 * It also shows that subproblems are overlapping since each problem depends of up to 6 sub-problems of decremented 
 * size.
 */
object NumberSolitaire {
    def solution(arr: Array[Int]): Int = {
        val maxScores = Array.ofDim[Int](arr.length)
        maxScores(0)  = arr(0)

        for (i <- 1 until arr.length) maxScores(i) = (1 to Math.min(i, 6)).map(dieFace => maxScores(i - dieFace) + arr(i)).max
        maxScores(maxScores.length - 1)
    }
}