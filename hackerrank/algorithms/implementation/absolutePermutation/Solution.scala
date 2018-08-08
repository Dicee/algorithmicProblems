package hackerrank.algorithms.implementation.absolutePermutation

// Difficulty: not too hard. I didn't immediately write it as concisely though, I first tried to break it down by different edge cases instead of
//             having a single codepath.

// https://www.hackerrank.com/challenges/absolute-permutation/problem
object Solution {
    def absolutePermutation(n: Int, k: Int): Array[Int] = {        
        val result = (1 to n).toArray
        if (searchSolution(result, n, k)) result else Array(-1)
    }
    
    def searchSolution(arr: Array[Int], n: Int, k: Int): Boolean = {
        for (i <- 0 until n) {
            if (arr(i) == i + 1) {
                if (i + k >= n || arr(i + k) != i + k + 1) return false 
                val tmp = arr(i)
                arr(i) = arr(i + k)
                arr(i + k) = tmp
            }
        }
        true
    }
}
