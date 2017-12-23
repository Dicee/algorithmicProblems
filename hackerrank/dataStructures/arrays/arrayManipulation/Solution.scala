package hackerrank.dataStructures.arrays.arrayManipulation

// Difficulty: I'll be honest, I didn't find the optimal, linear solution by myself. It's pretty ingenious but very easy to implement.
//             The best idea I could came up with was to implement an interval tree to find all intervals intersecting each of the cells
//             at the end of the m queries. I believe the complexity would have been O(n log(n + m)). The implementation would have been 
//             substantially more difficult than the linear solution.
//             Therefore, the 'Hard' tag attributed to this question by HackerRank makes sense.

// https://www.hackerrank.com/challenges/crush/problem
object Solution {
    def main(args: Array[String]) {
        val lines       = scala.io.Source.stdin.getLines()
        val Array(n, m) = lines.next().split(' ').map(_.toInt)

        val arr = Array.ofDim[Int](n)

        for (line <- lines) {
            val Array(low, high, k) = line.split(' ').map(_.toInt)
            arr(low  - 1) += k
            if (high < arr.length) arr(high) -= k
        }

        var (runningSum, max) = (BigInt(0), BigInt(0))
        for (i <- arr) {
            runningSum += i
            max = runningSum.max(max)
        }

        println(max)
    }
}
