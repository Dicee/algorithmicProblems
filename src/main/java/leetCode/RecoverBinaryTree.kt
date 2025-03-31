/**
 * Difficulty: medium-hard, marked as medium. I didn't find it particularly easy, I tried to make it work with a non-void recursive function
 *             carrying all the state in parameters but I was missing some edge cases where the whole tree needs to be traversed, as I was
 *             returning too early. The final solution is easy but for me it wasn't so easy to convince myself it covered all cases. I also
 *             didn't know about Morris traversal for a O(1) memory complexity.
 *
 *             Below, I added both the recursive solution and the one based on Morris traversal.
 *
 * https://leetcode.com/problems/recover-binary-search-tree/
 */
class RecursiveSolution {
    fun recoverTree(root: TreeNode?) {
        var firstInversion: TreeNode? = null
        var secondInversion: TreeNode? = null
        var prev: TreeNode? = null

        fun recSol(node: TreeNode?) {
            if (node == null) return
            recSol(node.left)

            if (prev != null && prev!!.`val` >= node.`val`) {
                if (firstInversion == null) firstInversion = prev
                secondInversion = node
            }
            prev = node

            recSol(node.right)
        }
        recSol(root)

        val tmp = firstInversion!!.`val`
        firstInversion!!.`val` = secondInversion!!.`val`
        secondInversion!!.`val` = tmp
    }
}

// based on https://how.dev/answers/what-is-morris-traversal
class MorrisTraversalRecursion {
    fun recoverTree(root: TreeNode?) {
        var firstInversion: TreeNode? = null
        var secondInversion: TreeNode? = null
        var prevInOrder: TreeNode? = null

        var node = root

        fun updateInversions() {
            if (prevInOrder != null && prevInOrder!!.`val` >= node!!.`val`) {
                if (firstInversion == null) firstInversion = prevInOrder
                secondInversion = node
            }
            prevInOrder = node
        }

        while (node != null) {
            if (node.left == null) {
                updateInversions()
                node = node.right
            }
            else {
                var rightMostLeftNode = node.left!!
                while (rightMostLeftNode.right != null && rightMostLeftNode.right != node) {
                    rightMostLeftNode = rightMostLeftNode.right!!
                }

                if (rightMostLeftNode.right == null) {
                    rightMostLeftNode.right = node
                    node = node.left
                } else {
                    rightMostLeftNode.right = null
                    updateInversions()
                    node = node.right
                }
            }
        }

        val tmp = firstInversion!!.`val`
        firstInversion!!.`val` = secondInversion!!.`val`
        secondInversion!!.`val` = tmp
    }
}

// provided by Leetcode
class TreeNode(var `val`: Int) {
     var left: TreeNode? = null
     var right: TreeNode? = null
 }
