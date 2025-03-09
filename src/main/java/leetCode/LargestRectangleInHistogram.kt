import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Difficulty: marked as difficult, but it's a classic. After a bit of thought, I found the solution back from my memories,
 *             with the exception of an edge case I had to re-discover, when the index of the bar to insert has to be set
 *             to the index of the smallest bar we removed from the stack.
 *
 * https://leetcode.com/problems/largest-rectangle-in-histogram
 */

data class Bar(val index: Int, val height: Int)

fun largestRectangleArea(heights: IntArray): Int {
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

// Executing in REPL
largestRectangleArea(intArrayOf(2, 1, 5, 6, 2, 3)) // 10
largestRectangleArea(intArrayOf(2, 4)) // 4
largestRectangleArea(intArrayOf(1, 2, 3, 8, 8, 10, 1, 2)) // 24
largestRectangleArea(intArrayOf(3, 4, 5, 6, 6, 2, 3, 2, 2, 2)) // 20
largestRectangleArea(intArrayOf(3, 4, 5, 6, 100, 2, 3, 2, 30, 40)) // 100
largestRectangleArea(intArrayOf(3, 4, 5, 6, 100, 2, 3, 2, 40, 40, 50)) // 120
largestRectangleArea(intArrayOf(1, 3, 8, 9, 5, 5, 1)) // 20
