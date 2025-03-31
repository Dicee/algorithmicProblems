/**
 * Difficulty: easy, marked as medium. I had to think a little bit to find a solution without division. The solution is not fast
 *             compared to other submissions, but it's probably way cuter thanks to Kotlin's expressiveness!
 *
 * https://leetcode.com/problems/product-of-array-except-self
 */
fun productExceptSelf(nums: IntArray): IntArray {
    val output = IntArray(nums.size) { 0 }

    val zeros = nums.asSequence().withIndex().filter { (_, v) -> v == 0 }.take(2).toList()
    if (zeros.size == 2) return output
    if (zeros.size == 1) {
        val z = zeros.first()
        output[z.index] = nums.foldIndexed(1) { j, acc, v -> if (z.index == j) acc else acc * v }
        return output
    }

    // the most efficient would be to divide but the problem statement explicitly asks not to
    val reversed = nums.reversedArray()
    val prefixProducts = nums.runningReduce { acc, v -> acc * v }
    val suffixProducts = reversed.runningReduce { acc, v -> acc * v }

    val last = output.lastIndex
    for (i in output.indices) {
        output[i] = when (i) {
            0    -> suffixProducts[last - 1]
            last -> prefixProducts[last - 1]
            else -> prefixProducts[i - 1] * suffixProducts[last - i - 1]
        }
    }

    return output
}

println(productExceptSelf(intArrayOf(1, 2, 3, 4)).toList()) // [24, 12, 8, 6]
println(productExceptSelf(intArrayOf(-1, 1, 0, -3, 3)).toList()) // [0, 0, 9, 0]
println(productExceptSelf(intArrayOf(0, -1, 1, -3, 3)).toList()) // [9, 0, 0, 0]
println(productExceptSelf(intArrayOf(1, 2, 3, 0, 5, 6, 0, 7)).toList()) // [0, 0, 0, 0, 0, 0, 0, 0]
