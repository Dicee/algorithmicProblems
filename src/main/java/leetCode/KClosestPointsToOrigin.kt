package leetCode

import kotlin.random.Random

// Difficulty: medium. If you know quick select it's very easy to think about it, and then just need to implement it. For some reason I failed to make
//             a working version making use of Hoare's partition scheme rather than Lomuto's, but I gave up when I realized that even a basic 2-lines
//             solution using an O(n log(n)) sort was performing similarly compared to quick select, which has a better theoretical complexity. Kind
//             of disappointing that the extra effort wasn't worth it, I guess it's because the TimSort implementation in the JDK is much more optimized
//             than my sorry stab at quick select!

// https://leetcode.com/problems/k-closest-points-to-origin/
fun main() {
    val a = point(1, 3)
    val b = point(-2, 2)
    val c = point(-1, 1)
    val d = point(-1, 0)
    val e = point(-10, 0)

    test(arrayOf(a, b), 1) // [[-2, 2]]
    test(arrayOf(a, b), 2) // [[-2, 2], [1, 3]]
    test(arrayOf(a, b, b, b), 3) // [[-2, 2], [-2, 2], [-2, 2]]
    test(arrayOf(a, b, b, b, b), 3) // [[-2, 2], [-2, 2], [-2, 2]]
    test(arrayOf(a, b, b, b, c), 2) // [[-1, 1], [-2, 2]]
    test(arrayOf(a, b, b, b, c), 3) // [[-1, 1], [-2, 2], [-2, 2]]
    test(arrayOf(a, b, c, d, e), 1) // [[-1, 0]]
    test(arrayOf(a, b, c, d, e), 2) // [[-1, 0], [-1, 1]]
    test(arrayOf(a, b, c, d, e), 3) // [[-1, 0], [-1, 1], [-2, 2]]
    test(arrayOf(a, b, c, d, e), 4) // [[-1, 0], [-1, 1], [-2, 2], [1, 3]]
    test(arrayOf(a, b, c, d, e), 5) // [[-1, 0], [-1, 1], [-2, 2], [1, 3], [-10, 0]]
    test(arrayOf(a, b, c, d, d, e), 5) // [[-1, 0], [-1, 0], [-1, 1], [-2, 2], [1, 3]]
    test(arrayOf(a, b, c, d, d, e), 3) // [[-1, 0], [-1, 0], [-1, 1]]
    test(arrayOf(
            point(10, -2),
            point(2, -2),
            point(10, 10),
            point(9, 4),
            point(-8, 1)
    ), 4) // [[10, -2], [2, -2], [-8, 1], [9, 4]]

    test(arrayOf(
            point(68, 97),
            point(34, -84),
            point(60, 100),
            point(2, 31),
            point(-27, -38),
            point(-73, -74),
            point(-55, -39),
            point(62, 91),
            point(62, 92),
            point(-57, -67),
    ), 5) // [[2, 31], [-27, -38], [-55, -39], [-57, -67], [34, -84]]
}

private fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
    quickSelect(points, k - 1)
    return points.sliceArray(0 until k)
}

private tailrec fun quickSelect(arr: Array<IntArray>, k: Int, start: Int = 0, end: Int = arr.lastIndex): IntArray {
    if (start == end) return arr[start]

    val pivot = lomutoPartition(arr, start, end)
    return when {
        pivot == k -> arr[pivot]
        pivot > k -> quickSelect(arr, k, start, pivot - 1)
        else -> quickSelect(arr, k, pivot + 1, end)
    }
}

// I tried with Hoare's partition scheme without getting to a correct implementation for some reason, and anyway the solution was already in a
// relatively good performance percentile relative to other Kotlin solutions, which was not even noticeably faster than a two lines solution
// using a sort! I guess either we fall into the worst-case scenario for quick select, or the size of the problem is not large enough to see
// the difference
private fun lomutoPartition(arr: Array<IntArray>, first: Int, last: Int): Int {
    val pivotIndex = Random.nextInt(first, last + 1)
    val pivot = arr[pivotIndex]
    swap(arr, pivotIndex, last)

    var i = first - 1
    for (j in first until last) {
        if (arr[j] <= pivot) swap(arr, ++i, j)
    }
    swap(arr, i + 1, last)
    return i + 1
}

private fun swap(arr: Array<IntArray>, i: Int, j: Int) {
    val tmp = arr[i]
    arr[i] = arr[j]
    arr[j] = tmp
}

// comparing squared distances prevents floating point imprecision. It cannot overflow because the problem specifies that
// x, y <= 10^4, so x^2 + y^2 <= 2 * 10^8 < 2^31 - 1
private operator fun IntArray.compareTo(other: IntArray) = this.squaredDistanceToOrigin().compareTo(other.squaredDistanceToOrigin())
private fun IntArray.squaredDistanceToOrigin() = this[0]*this[0] + this[1]*this[1]

private fun point(x: Int, y: Int) = intArrayOf(x, y)
private fun test(inputPoints: Array<IntArray>, k: Int) = println(kClosest(arrayOf(*inputPoints), k).asSequence().map { it.toList() }.toList())

