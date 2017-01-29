package hackerrank.dataStructures.disjointSet.componentsInAGraph

// Difficulty: once you've done Merging communities (https://www.hackerrank.com/challenges/merging-communities), this
// problem does not add much. It's pretty much exactly the same solution.

// https://www.hackerrank.com/challenges/components-in-graph
object Solution {
  def main(args: Array[String]): Unit = {
    val lines  = scala.io.Source.stdin.getLines
    val nNodes = lines.next().split(' ')(0).toInt

    val disjointSetsForest = new DisjointSetsForest(2 * nNodes)
    lines.take(nNodes).map(_.split(' ').map(_.toInt)).foreach {
      case Array(i, j) => disjointSetsForest.mergeSets(i.toInt, j.toInt)
    }

    val (min, max) = disjointSetsForest.smallestAndLargestSetSizes
    println(min + " " + max)
  }

  private class DisjointSetsForest(n: Int) {
    private val nodes: Array[Tree] = Array.fill(n)(new Tree)

    def mergeSets(i: Int, j: Int): Unit = {
      val iRoot = findContainingSet(nodes(i - 1))
      val jRoot = findContainingSet(nodes(j - 1))

      if (iRoot != jRoot) {
        if (iRoot.rank >= jRoot.rank) jRoot.parent = iRoot
        else                          iRoot.parent = jRoot
      }
    }

    def findContainingSetSize(i: Int): Int = findContainingSet(nodes(i - 1)).size

    private def findContainingSet(tree: Tree): Tree = {
      if (tree.parent == null) tree
      else {
        tree.parent = findContainingSet(tree.parent)
        tree.parent
      }
    }

    def smallestAndLargestSetSizes: (Int, Int) = nodes.toList
       .map(_.size)
       .filter(_ > 1)
       .map(size => (size, size))
       .foldLeft((Int.MaxValue, Int.MinValue))((t1, t2) => (Math.min(t1._1, t2._1), Math.max(t1._2, t2._2)))

    private class Tree {
      private var _parent: Tree  = _
      private var (_rank, _size) = (0, 1)

      def rank = _rank
      def size = _size

      def parent            = _parent
      def parent_=(t: Tree) = {
        _parent  = t
        t._size += _size
        // this tree is no longer a representative, its size is now accounted by its parent and we don't want to double count
        _size    = 0
        if (rank == t.rank) t._rank += 1
        ()
      }
    }
  }
}