/**
 * Difficulty: trivial, marked as medium. Just a bucket sort.
 *
 * https://leetcode.com/problems/sort-colors
 */
fun sortColors(nums: IntArray) {
    val buckets = IntArray(3)
    nums.forEach { buckets[it]++ }

    var j = 0
    for (i in nums.indices) {
        while (buckets[j] == 0) j++
        buckets[j]--

        nums[i] = j
    }
}

sortColors(intArrayOf(2, 0, 2, 1, 1, 0)) // [0,0,1,1,2,2]
sortColors(intArrayOf(2, 0, 1)) // [0,1,2]
