import kotlin.math.min
import kotlin.math.max

/**
 * Difficulty: easy, marked as medium. Pretty straightforward logic, some edge cases missed by my first attempt but quickly corrected.
 *
 * https://leetcode.com/problems/insert-interval
 */
fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
    var firstOverlapping = -1
    var lastOverlapping = -1
    var insertionPoint = intervals.size

    for ((i, interval) in intervals.withIndex()) {
        val overlaps = interval overlaps newInterval
        if (overlaps) {
            if (firstOverlapping < 0) firstOverlapping = i
            lastOverlapping = i
        }
        if (insertionPoint == intervals.size && (overlaps || interval[0] > newInterval[0])) insertionPoint = i
    }

    val n = intervals.size + (if (firstOverlapping >= 0) firstOverlapping - lastOverlapping else 1)
    val result = Array(n) { IntArray(0) }

    // copy everything before the insertion point
    for (i in 0 until insertionPoint) result[i] = intervals[i]

    // insert the non-overlapping new interval, or the merge of all overlapping intervals
    result[insertionPoint] = if (firstOverlapping == -1) newInterval else {
        val merged = IntArray(2)
        merged[0] = min(intervals[firstOverlapping][0], newInterval[0])
        merged[1] = max(intervals[lastOverlapping][1], newInterval[1])
        merged
    }

    // copy everything after the last non-overlapping interval past the insertion point
    for (i in insertionPoint + 1 until n) {
        val offset = if (firstOverlapping >= 0) firstOverlapping - lastOverlapping else 1
        result[i] = intervals[i - offset]
    }

    return result
}

private infix fun IntArray.overlaps(that: IntArray) = when {
    this[0] <= that[0] -> that[0] <= this[1]
    else -> this[0] <= that[1]
}

println(intervalsToStr(insert(arrayOf(intArrayOf(1, 3), intArrayOf(6, 9)), intArrayOf(2, 5)))) // [[1, 5], [6, 9]]
println(intervalsToStr(insert(arrayOf(intArrayOf(1, 5)), intArrayOf(0, 0)))) // [[0, 0], [1, 5]]
println(intervalsToStr(insert(
    arrayOf(
        intArrayOf(1, 2),
        intArrayOf(3, 5),
        intArrayOf(6, 7),
        intArrayOf(8, 10),
        intArrayOf(12, 16),
    ),
    intArrayOf(4, 8)
))) // [[1, 2], [3, 10], [12, 16]]

fun intervalsToStr(result: Array<IntArray>) = result.toList().map { it.toList() }

