package interviewbit.Programming.TreeDataStructure.LeastCommonAncestor

// Difficulty: medium. My code didn't look as concise when I first passed all tests, and I took 30 minutes longer than
//             the average to solve the problem. However, my solution is the only one I've seen that combines all of the
//             following optimizations:
//
//             - single pass versus up to 3 in some other solutions
//             - O(1) heap memory versus up to O(log(n)) in some other solutions
//             - no recursive call when unnecessary versus some other solutions always making those

// https://www.interviewbit.com/problems/least-common-ancestor/
object Solution {
  def lca(root: TreeNode, v: Int, w: Int): Int = {
    def recSol(node: TreeNode, vNode: TreeNode, wNode: TreeNode): (TreeNode, TreeNode, TreeNode) = {
      if (node == null) (node, vNode, wNode)
      else {
        val ( sol,  vn,  wn) = (null, if (node.value == v) node else vNode, if (node.value == w) node else wNode)
        val (lsol, lvn, lwn) = if ( vn != null &&  wn != null) ( sol,  vn,  wn) else recSol(node.left ,  vn,  wn)
        val (rsol, rvn, rwn) = if (lvn != null && lwn != null) (lsol, lvn, lwn) else recSol(node.right, lvn, lwn)
        if (rvn != null && rwn != null) (if (rsol != null) rsol else if (vNode == null && wNode == null) node else null, rvn, rwn)
        else (null, rvn, rwn)
      }
    }

    val sol = recSol(root, null, null)._1
    if (sol == null) -1 else sol.value
  }

  private class TreeNode(val value: Int, val left: TreeNode, val right: TreeNode)
}
