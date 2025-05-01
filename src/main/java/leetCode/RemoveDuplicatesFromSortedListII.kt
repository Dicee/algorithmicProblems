/**
 * Difficulty: relatively easy, marked as medium. Nothing special.
 *
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii
 */
fun deleteDuplicates(head: ListNode?): ListNode? {
    var newHead = head
    var prev: ListNode? = null
    var curr = head

    while (curr != null) {
        val value = curr.`val`
        var next = curr
        while (value == next?.`val`) next = next.next

        if (curr.next == next) {
            prev?.next = curr
            prev = curr
            curr = next
        } else {
            curr = next
            if (prev == null) newHead = next
            else prev.next = next
        }
    }

    return newHead
}

println(deleteDuplicates(ListNode(1, ListNode(2, ListNode(3, ListNode(3, ListNode(4, ListNode(4, ListNode(5))))))))) // [1, 2, 5]
println(deleteDuplicates(ListNode(1, ListNode(1, ListNode(1, ListNode(3, ListNode(4, ListNode(4, ListNode(5))))))))) // [3, 5]
println(deleteDuplicates(ListNode(-1, ListNode(0, ListNode(1, ListNode(1, ListNode(1, ListNode(1)))))))) // [-1, 0]
println(deleteDuplicates(ListNode(1, ListNode(1, ListNode(2, ListNode(2)))))) // []

class ListNode(var `val`: Int, var next: ListNode? = null) {
    override fun toString(): String {
        var node: ListNode? = this
        return buildString {
            append("[")
            while (node != null) {
                append(node!!.`val`)
                node = node!!.next
                if (node != null) append(", ")
            }
            append("]")
        }
    }
}
