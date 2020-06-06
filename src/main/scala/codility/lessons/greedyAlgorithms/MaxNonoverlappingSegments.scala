package codility.lesson15

object MaxNonoverlappingSegments {
    object Solution {
        /**
         * Because B[K] ≤ B[K + 1], for each K (0 ≤ K < N − 1), we can just iterate and count greedily from left to right
         */
        def solution(lower: Array[Int], upper: Array[Int]): Int = {
            def recSol(bounds: List[Bounds], nonOverLapping: Int = 0): Int = (bounds: @unchecked) match {
                case Nil                  => nonOverLapping
                case t :: Nil             => 1 + nonOverLapping
                case current :: next :: q => if (current.overlaps(next)) recSol(current :: q, nonOverLapping) else recSol(next :: q, nonOverLapping + 1)
            }
            recSol(lower.zip(upper).map(t => Bounds(t._1, t._2)).toList)
        }
    }
    
    case class Bounds(lower: Int, upper: Int) {
        def overlaps(that: Bounds) = Math.max(lower, that.lower) <= Math.min(upper, that.upper)
    }
}