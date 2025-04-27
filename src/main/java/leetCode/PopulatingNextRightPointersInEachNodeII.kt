/**
 * Difficult: easy, marked as medium. To be honest I missed an edge case (that forces having the while loop), but other than that it's quite
 *            easy.
 *
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii
 */
fun connect(node: Node?, parent: Node? = null): Node? {
    if (node == null) return null
    if (node.left != null && node.right != null) node.left!!.next = node.right

    if (node.next == null) {
        var parentOfNextCandidate = parent?.next
        while (parentOfNextCandidate != null) {
            val nextCandidate = parentOfNextCandidate.left ?: parentOfNextCandidate.right
            if (nextCandidate != null) {
                node.next = nextCandidate
                parentOfNextCandidate = null
            } else {
                parentOfNextCandidate = parentOfNextCandidate.next
            }
        }
    }

    // start with the right so that the next link is always ready for any left node that'd have to
    // follow multiple next parent links before finding its next node
    connect(node.right, node)
    connect(node.left, node)

    return node
}

fun verify(node: Node?) {
    if (node == null) return

    if (node.next != null) println("Next of ${node.`val`} is ${node.next!!.`val`}")

    verify(node.left)
    verify(node.right)
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val result = connect(
            Node(1,
                Node(2,
                    Node(4, left = Node(7)),
                    Node(5)
                ),
                Node(3, right = Node(6, right = Node(8)))
            )
        )

        verify(result)
    }
}

class Node(var `val`: Int, var left: Node? = null, var right: Node? = null) {
    var next: Node? = null
}
