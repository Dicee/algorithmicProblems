package interviewbit.Programming.TwoPointers.Array3Pointers

import scala.math._
import scala.annotation.tailrec

// Difficulty: easy (but fun to right in the most elegant possible way in functional style)

// https://www.interviewbit.com/problems/array-3-pointers/
object Solution {
  def main(args: Array[String]): Unit = {
      println(minimize(
        Array(1, 4, 10),
        Array(2, 15, 20),
        Array(10, 12)
      ))
  }

  def minimize(A: Array[Int], B: Array[Int], C: Array[Int]): Int  = {
    @tailrec
    def recSol(tuple: (List[Int], List[Int], List[Int]), minimax: Int): Int = tuple match {
      case (la @ a :: _, lb @ b :: _, lc @ c :: _) =>

        val (ab, ac, bc) = (abs(a - b), abs(a - c), abs(b - c))
        val maxAbsDiff   = max(max(ab, ac), bc)
        val newMinimax   = min(minimax, maxAbsDiff)

        if (newMinimax == 0) 0 // can't beat that, no need to go further
        else recSol(
              if      (ab == maxAbsDiff) advanceSmallest(la, lb, lc, 2)
              else if (ac == maxAbsDiff) advanceSmallest(la, lc, lb, 1)
              else                       advanceSmallest(lb, lc, la, 0),
            newMinimax)

      case (Nil, _, _) | (_, Nil, _) | (_, _, Nil) => minimax
    }

    def advanceSmallest(left: List[Int], right: List[Int], l: List[Int], i: Int) = {
      val (newLeft, newRight) = if (left.head < right.head) (left.tail, right) else (left, right.tail)
      i match {
        case 0 => (l      , newLeft , newRight)
        case 1 => (newLeft, l       , newRight)
        case 2 => (newLeft, newRight, l       )
      }
    }

    recSol((A.toList, B.toList, C.toList), Int.MaxValue)
  }
}