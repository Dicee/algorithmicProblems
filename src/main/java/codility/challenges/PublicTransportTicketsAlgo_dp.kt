package codility.challenges

import kotlin.math.min

const val ONE_DAY_COST = 2
const val ONE_WEEK_COST = 7
const val ONE_MONTH_COST = 25

/**
 * Muad'Dib 2022 challenge: https://app.codility.com/programmers/challenges/muaddib_2022/
 * Level: Hard
 * Score: 100% correctness and performance (gold)
 */
fun main() {
    println(dpSolution(intArrayOf(1, 2, 4, 5, 7, 29, 30))) // 11
    println(dpSolution(intArrayOf(1, 2, 4, 5, 7, 29))) // 9
    println(dpSolution(intArrayOf(1, 2, 4, 5, 7))) // 7
    println(dpSolution(intArrayOf(1, 2))) // 4
    println(dpSolution(intArrayOf(1, 2, 4, 7, 9, 10, 13, 15))) // 14
}

fun dpSolution(arr: IntArray): Int {
    val costs = IntArray(arr.last() + 1) { Int.MAX_VALUE }
    costs[0] = 0

    var i = 0

    for (j in 1..costs.lastIndex) {
        if (j < arr[i]) costs[j] = costs[j - 1]
        else {
            var k = 0
            while (j + k <= costs.lastIndex && k <= 29) {
                val cost = when {
                    k == 0 -> ONE_DAY_COST
                    k <= 6 -> ONE_WEEK_COST
                    else -> ONE_MONTH_COST
                }
                costs[j + k] = min(costs[j + k], costs[j - 1] + cost)
                k++
            }
            i++
        }
    }
    return costs.last()
}