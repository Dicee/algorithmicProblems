package interviewbit.Programming.TwoPointers.IntersectionOfSortedArrays

import scala.annotation.tailrec

// Difficulty: trivial

// https://www.interviewbit.com/problems/intersection-of-sorted-arrays/
object Solution {
    def intersect(left: Array[Int], right: Array[Int]): Array[Int]  = {
      @tailrec
      def recSol(left: List[Int], right: List[Int], acc: List[Int]): List[Int] = (left, right) match {
        case (l :: tl, r :: tr)  =>
          if      (l == r) recSol(tl  , tr   , l :: acc)
          else if (l >  r) recSol(tl  , right, acc     )
          else             recSol(left, tr   , acc     )

        case (Nil, _) | (_, Nil) => acc
      }
      recSol(left.view.reverse.toList, right.view.reverse.toList, Nil).toArray
    }
}
