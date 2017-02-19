package hackerrank.algorithms.bitManipulation.maxmizingXOR

import scala.annotation.tailrec

// Difficulty: fairly easy

// https://www.hackerrank.com/challenges/maximizing-xor
object Solution {
  private def reversedBits(n: Int): List[Boolean] = if (n <= 1) List(n == 1) else (n % 2 == 1) :: reversedBits(n >> 1)

  private def maxXOR(minBits: List[Boolean], maxBits: List[Boolean], acc: List[Boolean] = Nil) = {
    @tailrec
    def recSol(minBits: List[Boolean], maxBits: List[Boolean], bitLength: Int, canFlipBit: Boolean = false, acc: Int = 0): Int = (minBits, maxBits) match {
      case (minStrongestBit :: minTail, maxStrongestBit :: maxTail) =>
        val areOppositableBits = canFlipBit || minStrongestBit != maxStrongestBit
        recSol(minTail, maxTail, bitLength - 1, areOppositableBits, if (areOppositableBits) acc + (1 << bitLength) else acc)

      case _ => acc
    }
    recSol(minBits, maxBits, maxBits.length - 1)
  }

  def main(args: Array[String]): Unit = {
    val lines      = scala.io.Source.stdin.getLines()
    val (min, max) = (lines.next().toInt, lines.next().toInt)
    val maxBits    = reversedBits(max)
    val minBits    = reversedBits(min).padTo(maxBits.length, false)
    println(maxXOR(minBits.reverse, maxBits.reverse))
  }
}