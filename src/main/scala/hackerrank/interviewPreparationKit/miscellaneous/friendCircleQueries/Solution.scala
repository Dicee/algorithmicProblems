package hackerrank.interviewPreparationKit.miscellaneous.friendCircleQueries

import scala.collection.mutable
import scala.annotation.tailrec

// Difficulty: easy if you're already familiar with disjoint trees and can re-use a previous solution to a similar problem with slight modifications.

// https://www.hackerrank.com/challenges/friend-circle-queries/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=miscellaneous
object Solution {  
    def maxCircle(queries: Array[Array[Int]]): Array[Int] = {
        val forest = new DisjointSetForest
        var max    = 0
        
        (for {
            query <- queries.view
            res = query match {
                case Array(a, b) => 
                    forest.makeSet(a)
                    forest.makeSet(b)
                    max = Math.max(forest.size(forest.union(a, b)), max)
            }
        } yield max).toArray
    }
    
    private class DisjointSetForest() {
         // not creating an array containing all possible nodes upfront because here nodes are numbered 
        // from 1 to 10^9 while there are only 10^5 queries. We can therefore save plenty of space
        // using a sparse map even with the memory overhead of a map compared to an array.
         private val trees = new mutable.HashMap[Int, DisjointSetTree]
        
         def makeSet(i: Int): Unit = trees.getOrElseUpdate(i, new DisjointSetTree(i)) 

          @tailrec
          final def find(i: Int): Int = if (trees(i).parent == trees(i)) i else {
              // perform path splitting to reduce the height of the tree on our way to the representative of this set
              val parent = trees(i).parent
              trees(i).parent = trees(i).parent.parent  
              find(parent.id)
          }

          def union(i: Int, j: Int): Int = {
              val (reprId1, reprId2) = (find(i)       , find(j)       ) 
              val (repr1, repr2)     = (trees(reprId1), trees(reprId2))

              if (repr1 == repr2)      reprId1
              else if (repr1 >= repr2) { repr2.parent = repr1; reprId1 }
              else                     { repr1.parent = repr2; reprId2 }
          }
        
         def size(i: Int) = trees(find(i)).size
  
         private class DisjointSetTree(val id: Int) extends Ordered[DisjointSetTree] {
             private var _rank   = 0
             private var _parent = this
             private var _size   = 1

             def parent = _parent 
             def rank   = _rank
             def size   = { require(isRepr); _size }
             
             def parent_=(p: DisjointSetTree): Unit = {
                 if (p.rank == rank) p._rank += 1
                 if (isRepr) p._size += size 
                 _parent  = p
             }

             private def isRepr = parent == this
             override def compare(that: DisjointSetTree) = rank compare that.rank
         }
    }  
}
