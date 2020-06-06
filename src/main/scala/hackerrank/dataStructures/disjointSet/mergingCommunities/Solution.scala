package hackerrank.dataStructures.disjointSet.mergingCommunities

// Difficulty: difficult. I had a simple working implementation with linear complexity, but the optimal solution with
//             disjoint-set forests is not trivial for someone who isn't familiar to it (I wasn't !). I based my implementation
//             on https://en.wikipedia.org/wiki/Disjoint-set_data_structure#Disjoint-set_forests with a few modifications. This
//             optimal implementation is extremely fast as its running time is the inverse of the fast-growing Ackermann function,
//             according to the above Wiki page.

// https://www.hackerrank.com/challenges/merging-communities
object Solution {
  def main(args: Array[String]): Unit = {
    val lines   = scala.io.Source.stdin.getLines
    val nPeople = lines.next().split(' ')(0).toInt

    val communities = new DisjointSetForest(nPeople)
    lines.map(_.split(' ')).foreach {
      case Array("M", i, j) => communities.mergeSets(i.toInt, j.toInt)
      case Array("Q", i   ) => println(communities.findContainingSetSize(i.toInt))
    }
  }

  private class DisjointSetForest(n: Int) {
    private val nodes = Array.fill(n)(new Tree)

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
