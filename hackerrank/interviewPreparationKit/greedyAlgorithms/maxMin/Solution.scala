package hackerrank.interviewPreparationKit.greedyAlgorithms.maxMin

// Difficulty: trivial

// https://www.hackerrank.com/challenges/angry-children/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=greedy-algorithms
object Solution {
    def maxMin(k: Int, arr: Array[Int]): Int = {
        val sortedValues = arr.view.sorted.toList
        sortedValues.zip(sortedValues.drop(k - 1)).map { case (a, b) => b - a }.min
    }
}
