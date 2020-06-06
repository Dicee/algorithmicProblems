package codility.lesson1

import scala.annotation.tailrec

object Solution {
    def solution(n: Int) = {
        @tailrec
        def recSol(n: Int, gap: Int = 0, hasSeenAOne: Boolean = false, maxGap: Int = 0): Int = n match {
            case 0               => maxGap
            case _ if n % 2 == 0 => recSol(n >> 1, gap + (if (hasSeenAOne) 1 else 0) , hasSeenAOne, maxGap)
            case _               => recSol(n >> 1, 0, true, Math.max(gap, maxGap))
        }
        recSol(n)
    }
}
