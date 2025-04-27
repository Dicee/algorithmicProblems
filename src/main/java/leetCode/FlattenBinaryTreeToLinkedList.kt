/**
 * Difficulty: easy, marked as medium. I thought about a Morris traversal to meet the O(1) requirement, but in the end it
 *             looks a bit different since the operation we need to perform also modifies the links in the tree.
 *
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list
 */
fun flattenConstantSpace(root: TreeNode?): Unit {
    var node = root
    while (node != null) {
        if (node.left == null) {
            node = node.right
        } else  {
            var rightMostLeftNode = node.left
            while (rightMostLeftNode.right != null) {
                rightMostLeftNode = rightMostLeftNode.right
            }

            rightMostLeftNode.right = node.right
            node.right = node.left
            node.left = null
            node = node.right
        }
    }
}
