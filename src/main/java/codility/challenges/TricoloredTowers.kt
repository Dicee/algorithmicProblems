package codility.challenges

import kotlin.math.max

/**
 * Link: https://app.codility.com/programmers/task/tricolored_towers/
 * Level: Medium
 * Score: 100% correctness and performance
 */
fun main() {
    println(solution(arrayOf("aab", "cab", "baa", "baa"))) // 3
    println(solution(arrayOf("zzz", "zbz", "zbz", "dgf"))) // 2
    println(solution(arrayOf("abc", "cba", "cab", "bac", "bca"))) // 3
}

fun solution(towers: Array<String>): Int {
    val possibilities = mutableMapOf<String, Int>()
    var maxCount = 0

    fun updatePossibilities(tower: String, permutation: String = tower, countSelf: Boolean = true) {
        if (tower == permutation && !countSelf) return
        val count = possibilities.merge(permutation, 1) { a, b -> a + b }!!
        maxCount = max(maxCount, count)
    }

    for (tower in towers) {
        updatePossibilities(tower)
        updatePossibilities(tower, "${tower[1]}${tower[0]}${tower[2]}", false)
        updatePossibilities(tower, "${tower[0]}${tower[2]}${tower[1]}", false)
    }

    return maxCount
}

