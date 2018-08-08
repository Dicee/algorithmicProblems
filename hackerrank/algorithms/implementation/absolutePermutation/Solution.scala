package hackerrank.algorithms.implementation.absolutePermutation

// Difficulty: not too hard. I didn't immediately write it as concisely though, I first tried to break it down by different edge cases instead of
//             having a single codepath.

// https://www.hackerrank.com/challenges/absolute-permutation/problem
object Solution {
    def absolutePermutation(n: Int, k: Int): Array[Int] = {        
        val result = (1 to n).toArray
        if (searchSolution(result, n, k)) result else Array(-1)
    }
    
    // Each element i can be moved to at most to positions (i + k and i - k). However, elements between 0 and k - 1
    // and only be moved forward, which means that the element that is displaced by moving i (< k) to i + k is the only
    // element that can fill the void left by i after it moved forward. Therefore, any solution *must* swap i and i + k.
    // In the end, there is actually a single possible position for each element. In some cases, some elements will need 
    // to be moved to the same position, rendering the problem impossible.
    def searchSolution(arr: Array[Int], n: Int, k: Int): Boolean = {
        for (i <- 0 until n) {
            // otherwise, the element has already been swapped
            if (arr(i) == i + 1) {
                // the element we need to swap with has already been swapped, no solution
                if (i + k >= n || arr(i + k) != i + k + 1) return false 
                val tmp = arr(i)
                arr(i) = arr(i + k)
                arr(i + k) = tmp
            }
        }
        true
    }
}
