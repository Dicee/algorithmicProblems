import java.util.Comparator.naturalOrder
import java.util.PriorityQueue

/**
 * Difficulty: medium, marked as hard. I didn't think of the deque solution myself and instead used a heap, even though it's a
 *             similar idea to the largest rectangle in a histogram, and even simpler.
 *
 * https://leetcode.com/problems/sliding-window-maximum
 */
fun maxSlidingWindowLinear(nums: IntArray, k: Int): IntArray {
    if (k > nums.size) return intArrayOf()

    val result = IntArray(nums.size - k + 1)
    val deque = ArrayDeque<Int>()

    for (i in nums.indices) {
        // drop elements smaller from the current one because they will not impact the maximum of the window,
        // and to maintain a decreasing order
        while (!deque.isEmpty() && deque.last() < nums[i]) deque.removeLast()

        deque.addLast(nums[i]) // guaranteed to be the smallest of the deque since we maintain a decreasing order

        if (i >= k && deque.first() == nums[i - k]) deque.removeFirst()
        if (i >= k - 1) result[i - k + 1] = deque.first()
    }

    return result
}

fun maxSlidingWindowNLogN(nums: IntArray, k: Int): IntArray {
    if (k > nums.size) return intArrayOf()

    val result = IntArray(nums.size - k + 1)
    val heap = PriorityQueue(naturalOrder<Int>().reversed())

    for (i in 0 until k){
        heap.add(nums[i])
    }
    result[0] = heap.peek()

    for (i in 1 until result.size) {
        heap.remove(nums[i - 1])
        heap.add(nums[i + k - 1])

        result[i] = heap.peek()
    }

    return result
}

println(maxSlidingWindowLinear(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3).contentToString()) // [3, 3, 5, 5, 6, 7]
println(maxSlidingWindowLinear(intArrayOf(), 1).contentToString()) // []

maxSlidingWindowNLogN(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3).contentToString() // [3, 3, 5, 5, 6, 7]
maxSlidingWindowNLogN(intArrayOf(), 1).contentToString() // []
