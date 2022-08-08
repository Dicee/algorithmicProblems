package codility.challenges

/**
 * Jurassic code 2022 challenge: https://app.codility.com/programmers/challenges/jurassic_code2022/
 * Level: Medium
 * Score: 100% correctness and performance (gold)
 */
fun main() {
    test(intArrayOf(4, 0, 2, -2), intArrayOf(4, 1, 2, -3), "RGRR", 2)
    test(intArrayOf(1, 1, -1, -1), intArrayOf(1, -1, 1, -1), "RGRG", 4)
    test(intArrayOf(1, 0, 0), intArrayOf(0, 1, -1), "GGR", 0)
    test(intArrayOf(5, -5, 5), intArrayOf(1, -1, -3), "GRG", 2)
    test(intArrayOf(3000, -3000, 4100, -4100, -3000), intArrayOf(5000, -5000, 4100, -4100, 5000), "RRGRG", 2)
    test(intArrayOf(0, 0, 4, 1), intArrayOf(5, -5, 3, 1), "RRRG", 0)
    test(intArrayOf(0, 0, 4, 1, 2), intArrayOf(5, -5, 3, 1, 2), "RRRGG", 0)
    test(intArrayOf(0, 0, 4, 1, 2, -4), intArrayOf(5, -5, 3, 1, 2, -3), "RRRGGG", 6)
    test(intArrayOf(0, 0, 4, 1, 2, 10), intArrayOf(5, -5, 3, 1, 2, -3), "RRRGGG", 6)
}

private fun largestBalancedRadius(xs: IntArray, ys: IntArray, colors: String): Int {
    val sortedDistWithColor = xs.indices.asSequence()
        .map { (xs[it].toLong() * xs[it].toLong() + ys[it].toLong() * ys[it].toLong()) to colors[it] }
        .sortedBy { it.first }
        .toList()

    var (redPoints, greenPoints) = 0 to 0
    var (maxPointsInCircle, i) = 0 to 0

    while (i < sortedDistWithColor.size) {
        val currentDist = sortedDistWithColor[i].first
        while (i < sortedDistWithColor.size && sortedDistWithColor[i].first == currentDist) {
            val color = sortedDistWithColor[i++].second
            if (color == 'R') redPoints++ else greenPoints++
        }

        if (redPoints == greenPoints) maxPointsInCircle = redPoints + greenPoints
    }

    return maxPointsInCircle
}

private fun test(xs: IntArray, ys: IntArray, colors: String, expected: Int) {
    assert(largestBalancedRadius(xs, ys, colors) == expected)
}
