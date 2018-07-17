package hackerrank.algorithms.graphTheory.roadsAndLibraries

import scala.collection.mutable
import scala.annotation.tailrec

// Difficulty: medium. If you disjoint sets and have the intuition they can be useful there, you will eventually find out about
//             Kruskal's algorithm. Otherwise, it could be tricky to come up with a fast solution to this problem although a naive
//             one remains straightforward.

// https://www.hackerrank.com/challenges/torque-and-development/problem?h_l=playlist&slugs[]=interview&slugs[]=interview-preparation-kit&slugs[]=graphs
object Solution {
    def roadsAndLibraries(n: Int, libraryCost: Long, roadCost: Long, edges: Array[Array[Int]]): Long = {
        if (libraryCost <= roadCost) n * libraryCost 
        else {
            val forest = new DisjointSetForest(n)
            var roads = 0L

            // Kruskal's algorithm
            for (Array(c1, c2) <- edges; if forest.find(c1 - 1) != forest.find(c2 - 1)) {
                roads += 1
                forest.union(c1 - 1, c2 - 1)
            }

            val disjointSets = (for (i <- 0 until n; if forest.find(i) == i) yield i).length.toLong    
            
            println(disjointSets * libraryCost + roads * roadCost)
            disjointSets * libraryCost + roads * roadCost
        }
    }
    
    private class DisjointSetForest(n: Int) {
          private val trees = (0 until n).map(new DisjointSetTree(_))

          @tailrec
          final def find(i: Int): Int = if (trees(i).parent == trees(i)) i else {
            // perform path splitting to reduce the height of the tree on our way to the representative of this set
            val parent = trees(i).parent
            trees(i).parent = trees(i).parent.parent  
            find(parent.id)
          }

          def union(i: Int, j: Int): Unit = {
            val (repr1, repr2) = (trees(find(i)), trees(find(j)))
            if (repr1 != repr2) {
              if (repr1 >= repr2) repr2.parent = repr1
              else                repr1.parent = repr2
            }
          }
  
          private class DisjointSetTree(val id: Int) extends Ordered[DisjointSetTree] {
            private var _rank = 0
            private var _parent = this

            def parent = _parent
            def rank = _rank

            def parent_=(p: DisjointSetTree): Unit = {
              parent._rank += 1
              _parent = p
            }

            override def compare(that: DisjointSetTree) = rank compare that.rank
          }
    }
}
