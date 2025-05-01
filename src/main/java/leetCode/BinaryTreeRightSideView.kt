/**
 * Difficulty: trivial, marked as medium. Nothing special
 * 
 * https://leetcode.com/problems/binary-tree-right-side-view
 */
fun rightSideView(root: TreeNode?): List<Int> {
    val result: MutableList<Int> = mutableListOf()
    if (root == null) return result

    var border = mutableListOf(root)

    while (border.isNotEmpty()) {
        val children: MutableList<TreeNode> = mutableListOf()
        result.add(border.last().`val`)

        for (i in border.indices) {
            val node = border[i]
            if (node.left != null) children.add(node.left!!)
            if (node.right != null) children.add(node.right!!)
        }

        border = children
    }

    return result
}

println(
    rightSideView(
        TreeNode(
            1,
            TreeNode(2, TreeNode(4, TreeNode(5))),
            TreeNode(3)
        )
    )
) // [1, 3, 4, 5]

class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)
