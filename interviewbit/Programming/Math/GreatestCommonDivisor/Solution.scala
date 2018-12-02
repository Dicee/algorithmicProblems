package interviewbit.Programming.Math.GreatestCommonDivisor

import scala.math._
import scala.annotation.tailrec

// Difficulty: trivial

// https://www.interviewbit.com/problems/greatest-common-divisor/
object Solution {
  def main(args: Array[String]): Unit = {
    println(gcd(30, 42)) // 6
    println(gcd(10, 15)) // 5
    println(gcd(5, 15)) // 5
    println(gcd(330, 90)) // 30
  }

  def gcd(a: Int, b: Int) = {
    @tailrec
    def recSol(a: Int, b: Int): Int = {
      val r = a % b
      if (r == 0) b else recSol(b, r)
    }
    if (a == 0) b else if (b == 0) a else recSol(max(a, b), min(a, b))
  }
}
