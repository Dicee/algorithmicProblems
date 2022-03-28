package unknownSource

object Subtree {
  //           0
  //         /   \
  //		    1     6
  //       / \   / \
  //      2   4 7   8
  //     /   /     / \
  //    3	  5     9  10
  //	           /   / \
  //			      11  12 13
  private val TREE = new BinaryTree(0,
    new BinaryTree(1,
      new BinaryTree(2,
        new BinaryTree(3, null, null),
        null
      ),
      new BinaryTree(4,
        new BinaryTree(5, null, null),
        null
      )
    ),
    new BinaryTree(6,
      new BinaryTree(7, null, null),
      new BinaryTree(8,
        new BinaryTree(9,
          new BinaryTree(11, null, null),
          null
        ),
        new BinaryTree(10,
          new BinaryTree(12, null, null),
          new BinaryTree(13, null, null)
        )
      )
    )
  )
  def main(args: Array[String]): Unit = {
    println(TREE hasSubtree TREE) // true
    println(TREE hasSubtree TREE.left.right.left) // true
    println(TREE.left.right.left hasSubtree TREE) // false
    println(TREE hasSubtree TREE.right) // true
    println(TREE hasSubtree TREE.right.right) // true
    println(TREE hasSubtree new BinaryTree(100, TREE.right.right, null)) // false
  }
}

private class BinaryTree(val value: Int, val left: BinaryTree, val right: BinaryTree) {
  def hasSubtree(that: BinaryTree) = {
    // equality is kinda expensive, but shielding the equality check with a cheap hashCode check should prevent us from making unnecessary comparisons
    def recSol(child: BinaryTree): Boolean = {
      if (child == null) false
      else if (child.hashCode == that.hashCode && child == that) true
      else recSol(child.left) || recSol(child.right)
    }

    if (that == null) false
    else recSol(this)
  }

  override lazy val hashCode: Int = {
    val prime = 31
    (if (left == null) 0 else left.hashCode) * prime +
      (if (right == null) 0 else right.hashCode) * prime
  }

  override def equals(that: Any): Boolean = {
    def recSol(tree1: BinaryTree, tree2: BinaryTree): Boolean = {
      if (tree1 == null && tree2 == null) true
      else if (tree1 == null || tree2 == null) false
      else if (tree1.value != tree2.value) false
      else recSol(tree1.left, tree2.left) && recSol(tree1.right, tree2.right)
    }

    that match {
      case tree: BinaryTree => recSol(this, tree)
      case _                => false
    }
  }
}
