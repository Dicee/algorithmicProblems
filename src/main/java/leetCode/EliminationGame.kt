/**
 * Difficulty: easy, marked as medium. I really liked it because it reminded me when I was young and doing some maths.
 *             The idea is not that difficult to find, it doesn't take long to realize that it has a recursive structure
 *             and then it's just about using the right formula for each case (parity and direction). Enjoyable though,
 *             as it's very elegant and efficient for what it does.
 *
 * https://leetcode.com/problems/elimination-game
 */
fun lastRemaining(n: Int, reverse: Boolean = false): Int {
    if (n == 1) return n
    if (n == 2) return if (reverse) 1 else 2

    val newMax = n / 2
    val parity = if (reverse) 1 - n % 2 else 0
    return 2 * (lastRemaining(newMax, !reverse) - parity) + parity
}

println(lastRemaining(1)) // 1
println(lastRemaining(2)) // 2
println(lastRemaining(9)) // 6
println(lastRemaining(11)) // 8
