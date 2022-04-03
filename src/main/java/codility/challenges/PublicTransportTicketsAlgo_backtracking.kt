package codility.challenges

import kotlin.math.min

/**
 * Muad'Dib 2022 challenge: https://app.codility.com/programmers/challenges/muaddib_2022/.
 * Level: Hard
 * Score: 100% correctness and performance (gold)
 *
 * This solution is less efficient than the dynamic programming solution (also implemented in this package),
 * but I did it for fun. Predictably, it doesn't perform well enough to pass the largest tests (either times out,
 * or blows up with a stack overflow error).
 */
fun main() {
    println(backTrackingSolution(intArrayOf(1, 2, 4, 5, 7, 29, 30))) // 11
    println(backTrackingSolution(intArrayOf(1, 2, 4, 5, 7, 29))) // 9
    println(backTrackingSolution(intArrayOf(1, 2, 4, 5, 7))) // 7
    println(backTrackingSolution(intArrayOf(1, 2))) // 4
    println(backTrackingSolution(intArrayOf(1, 2, 4, 7, 9, 10, 13, 15))) // 14
}

fun backTrackingSolution(arr: IntArray): Int {
 return backTrackingSolution(arr, 0, 0, -1)
}

fun backTrackingSolution(arr: IntArray, i: Int, cost: Int, maxValidity: Int): Int {
    if (i == arr.lastIndex) return if (maxValidity >= arr.last()) cost else cost + ONE_DAY_COST

    if (maxValidity >= arr[i]) return backTrackingSolution(arr, i + 1, cost, maxValidity)

    val sol1 = backTrackingSolution(arr, i + 1, cost + ONE_DAY_COST, arr[i])
    val sol2 = backTrackingSolution(arr, i + 1, cost + ONE_WEEK_COST, arr[i] + 6)
    val sol3 = backTrackingSolution(arr, i + 1, cost + ONE_MONTH_COST, arr[i] + 29)
    return min(sol1, min(sol2, sol3))
}