package codility.lessons.primeAndCompositeNumbers

import scala.annotation.tailrec

// this solution is O(n*sqrt(n)) instead of O(n), but scores 100 %
object Flags {
  def solution(A: Array[Int]): Int = {
  		val peaks = A.zipWithIndex.filter { case (x, i) => 0 < i && i < A.length - 1 && A(i - 1) < x && A(i + 1) < x }.map(_._2)
  		@tailrec def recSol(minDist: Int, current: Int = 0, next: Int = 1, count: Int = 1): Int = {
  			if      (minDist <= 2 || minDist == count       ) minDist
  			else if (next >= peaks.length                   ) recSol(minDist - 1)
  			else if (peaks(next) - peaks(current) >= minDist) recSol(minDist, next, next + 1, count + 1)
  			else                                              recSol(minDist, current, next + 1, count)
  		} 
  		if (peaks.length <= 2) peaks.length 
  		else                   recSol(Math.sqrt(A.length).floor.toInt + 1)
    }
  
  // O(n*log(n))
  def solutionBisection(A: Array[Int]): Int = {
        val peaks = A.zipWithIndex.filter { case (x, i) => 0 < i && i < A.length - 1 && A(i - 1) < x && A(i + 1) < x }.map(_._2)
  		
  		@tailrec def canSetFlags(nFlags: Int, current: Int = 0, next: Int = 1, count: Int = 1): Boolean = {
  			if      (nFlags <= 2 || nFlags == count         ) true
  			else if (next         >= peaks.length           ) false
  			else if (peaks(next) - peaks(current) >= nFlags ) canSetFlags(nFlags, next   , next + 1, count + 1)
  			else                                              canSetFlags(nFlags, current, next + 1, count    )
  		} 
  		
  		@tailrec def bisection(lowerBound: Int, upperBound: Int, max: Int = 2): Int = {
  			if (lowerBound > upperBound) max
  			else {
	  			val mid = (upperBound + lowerBound)/2
	  			if (canSetFlags(mid)) bisection(mid + 1, upperBound, mid)
	  			else                  bisection(lowerBound, mid - 1, max)
  			}
  		}
  		
  		if (peaks.length <= 2) peaks.length 
  		else   			       bisection(2, Math.sqrt(A.length).floor.toInt + 1)
    }
}