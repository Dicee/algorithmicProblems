/**
 * Difficulty: easy, marked as hard. My solution could be a little simpler and use a little less space, but it's reasonable and it was
 *             pretty easy to come up with.
 * 
 * https://leetcode.com/problems/max-points-on-a-line
 */

fun maxPoints(points: Array<IntArray>): Int {
    if (points.size == 1) return 1

    val equations = mutableMapOf<LinearEquation, MutableSet<Point>>()
    var max = 0

    for (i in 0 until points.lastIndex) {
        for (j in i + 1..points.lastIndex) {
            val (xi, yi) = points[i]
            val (xj, yj) = points[j]

            val equation = when {
                xi == xj -> LinearEquation(1.0, 0.0, xi.toDouble())
                else     -> {
                    val a = (yj.toDouble() - yi) / (xj - xi)
                    val b = -1.0
                    val c = a * xi + b * yi
                    LinearEquation(a, b, c)
                }
            }
            val linePoints = equations.computeIfAbsent(equation) { mutableSetOf() }
            linePoints += Point(xi, yi)
            linePoints += Point(xj, yj)

            if (linePoints.size > max) {
                max = linePoints.size
            }
        }
    }

    return max
}

data class LinearEquation(val a: Double, val b: Double, val c: Double)
data class Point(val x: Int, val y: Int)

maxPoints(arrayOf()) // 0
maxPoints(arrayOf(intArrayOf(0, 0))) // 1
maxPoints(arrayOf(intArrayOf(1, 1), intArrayOf(2, 2), intArrayOf(3, 3))) // 3
maxPoints(arrayOf(
    intArrayOf(1, 1),
    intArrayOf(3, 2),
    intArrayOf(5, 3),
    intArrayOf(4, 1),
    intArrayOf(2, 3),
    intArrayOf(1, 4),
)) // 4
maxPoints(arrayOf(
    intArrayOf(0, 0),
    intArrayOf(4, 5),
    intArrayOf(7, 8),
    intArrayOf(8, 9),
    intArrayOf(5, 6),
    intArrayOf(3, 4),
    intArrayOf(1, 1),
)) // 5
maxPoints(arrayOf(
    intArrayOf(0, 0),
    intArrayOf(0, 100),
    intArrayOf(0, 58),
    intArrayOf(1, 1),
    intArrayOf(2, 2),
    intArrayOf(0, 4),
    intArrayOf(7, 10),
)) // 4
