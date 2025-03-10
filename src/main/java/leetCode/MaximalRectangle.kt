import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Difficulty: marked as hard but pretty classic. It's as hard as the historgram problem, if you already know the solution. Fun fact,
 *             the first time I encountered the matrix version was actually at work, and I found the solution back then. It was quite
 *             cool to be able to solve an algorithmic puzzle for an actual reason.
 *
 * https://leetcode.com/problems/maximal-rectangle/
 */

class Solution {
    data class Bar(val index: Int, val height: Int)

    fun maximalRectangle(matrix: Array<CharArray>): Int {
        val heightsMatrix = Array(matrix.size) { IntArray(matrix[0].size) { 0 } }
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                val heightAbove = if (i > 0) heightsMatrix[i - 1][j] else 0
                heightsMatrix[i][j] = if (matrix[i][j] == '1') 1 + heightAbove else 0
            }
        }
        return heightsMatrix.asSequence().maxOf { largestRectangleInHistogram(it) }
    }

    fun largestRectangleInHistogram(heights: IntArray): Int {
        if (heights.isEmpty()) return 0

        val deque = ArrayDeque<Bar>()
        var maxRectangle = 0

        for (i in heights.indices) {
            val bar = Bar(i, heights[i])

            if (i == 0) deque.addFirst(bar)
            else {
                var j = i
                val tallestBar = Bar(i - 1, heights[i - 1])
                while (deque.isNotEmpty() && deque.first().height > bar.height) {
                    val previousBar = deque.removeFirst()
                    maxRectangle = max(maxRectangle, areaBetween(tallestBar, previousBar))
                    j = previousBar.index
                }
                deque.addFirst(bar.copy(index = j))
            }
        }

        if (deque.isNotEmpty()) {
            val tallestBar = Bar(heights.lastIndex, heights.last())
            while (deque.isNotEmpty()) {

                val bar2 = deque.removeFirst()
                val b = areaBetween(tallestBar, bar2)
                maxRectangle = max(maxRectangle, b)
            }
        }

        return maxRectangle
    }

    fun areaBetween(bar1: Bar, bar2: Bar): Int = min(bar1.height, bar2.height) * (abs(bar1.index - bar2.index) + 1)
}
