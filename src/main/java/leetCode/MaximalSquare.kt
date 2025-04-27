import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Difficulty: marked as hard but pretty classic. I just slightly modified my solution for the maximal rectangle.
 *
 * https://leetcode.com/problems/maximal-square/
 */

class Solution {
    data class Bar(val index: Int, val height: Int)

    fun maximalSquare(matrix: Array<CharArray>): Int {
        val heightsMatrix = Array(matrix.size) { IntArray(matrix[0].size) { 0 } }
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                val heightAbove = if (i > 0) heightsMatrix[i - 1][j] else 0
                heightsMatrix[i][j] = if (matrix[i][j] == '1') 1 + heightAbove else 0
            }
        }
        return heightsMatrix.asSequence().maxOf { largestSquareInHistogram(it) }
    }

    fun largestSquareInHistogram(heights: IntArray): Int {
        if (heights.isEmpty()) return 0

        val deque = ArrayDeque<Bar>()
        var maxSquare = 0

        for (i in heights.indices) {
            val bar = Bar(i, heights[i])

            if (i == 0) deque.addFirst(bar)
            else {
                var j = i
                val tallestBar = Bar(i - 1, heights[i - 1])
                while (deque.isNotEmpty() && deque.first().height > bar.height) {
                    val previousBar = deque.removeFirst()
                    maxSquare = max(maxSquare, maxSquareBetween(tallestBar, previousBar))
                    j = previousBar.index
                }
                deque.addFirst(bar.copy(index = j))
            }
        }

        if (deque.isNotEmpty()) {
            val tallestBar = Bar(heights.lastIndex, heights.last())
            while (deque.isNotEmpty()) {
                maxSquare = max(maxSquare, maxSquareBetween(tallestBar, deque.removeFirst()))
            }
        }

        return maxSquare
    }

    fun maxSquareBetween(bar1: Bar, bar2: Bar): Int {
        val side = min(abs(bar1.index - bar2.index) + 1, min(bar1.height, bar2.height))
        return side * side
    }
}

