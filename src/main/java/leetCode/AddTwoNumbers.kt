/**
 * Difficulty: trivial, marked as easy. Could make it more efficient iteratively, but just nicer to read in recursive style.
 *
 * https://leetcode.com/problems/add-two-numbers
 */
fun addTwoNumbers(l1: ListNode?, l2: ListNode?, carryOver: Int = 0): ListNode? {
    if (l1 == null && l2 == null) {
        return if (carryOver == 0) null else ListNode(carryOver)
    }
    
    val sum = (l1?.`val` ?: 0) + (l2?.`val` ?: 0) + carryOver
    val (digit, newCarryOver) = sum % 10 to sum / 10
    
    val node = ListNode(digit)
    node.next = addTwoNumbers(l1?.next, l2?.next, newCarryOver)
    return node
}
