package hackerrank.algorithms.search.maximumSubarraySum

// Difficulty: quite challenging, I enjoyed it. I'm glad Java offers nice search methods on BSTs, otherwise my solution 
//             would have been much more tedious to implement.

// https://www.hackerrank.com/challenges/maximum-subarray-sum/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=search
object Solution {
    def maximumSum(arr: Array[Long], m: Long): Long = {
        // java.util.TreeSet has useful methods from NavigableSet that are not present in the Scala implementation. A custom binary search tree implementation would be required otherwise.
        val mods = new java.util.TreeSet[Long]
        var (mod, maxSum) = (0L, 0L)

        for ((value, i) <- arr.view.zipWithIndex) {
            mod = (mod + value) % m
            mods.add(mod)

            val minimax = mods.higher(mod)
            if (minimax != null) maxSum = Math.max(maxSum, (m + mod - minimax) % m)
            maxSum = Math.max(maxSum, mod)
        }
        maxSum
    }
}
