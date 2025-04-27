/**
 * Difficulty: trivial, marked as medium. No difficult in it.
 *
 * https://leetcode.com/problems/simplify-path
 */
fun rotateRight(head: ListNode?, k: Int): ListNode? {
    if (head?.next == null || k == 0) return head

    var node = head
    var last = head
    var size = 0

    while (node != null) {
        size += 1
        last = node
        node = node.next
    }

    val shift = k % size
    if (shift == 0) return head

    node = head

    for (i in 1..size - shift) {
        val next = node!!.next
        if (i == size - shift) node.next = null
        node = next
    }

    last!!.next = head
    return node
}

// provided by Leetcode
class ListNode(var `val`: Int) { var next: ListNode? = null }
