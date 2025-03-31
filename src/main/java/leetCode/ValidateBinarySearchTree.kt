/**
 * Difficulty: trivial, marked as medium. Really no difficulty whatsoever.
 *
 * https://leetcode.com/problems/validate-binary-search-tree/
 */

fun isValidBST(root: TreeNode?): Boolean {
    fun recSol(root: TreeNode?, minValue: Int?, maxValue: Int?): Boolean {
        if (root == null) return true
        if (minValue != null && root.`val` <= minValue) return false
        if (maxValue != null && root.`val` >= maxValue) return false
        return recSol(root.left, minValue, root.`val`) && recSol(root.right, root.`val`, maxValue)
    }
    return recSol(root, null, null)
}

// provided by LeetCode
class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

