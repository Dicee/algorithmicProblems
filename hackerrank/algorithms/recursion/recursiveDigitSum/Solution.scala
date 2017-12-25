package hackerrank.algorithms.recursion.recursiveDigitSum

import scala.annotation.tailrec

// Difficulty: easy. The author tried to make it harder by providing absolutely huge integers in input but as long as
//             they're never parsed as integers and the characters not stored in memory it's not that much of a challenge.
//             The recursive part is as trivial as it gets.

// https://www.hackerrank.com/challenges/recursive-digit-sum/problem
object Solution {
  def main(args: Array[String]) {
    val stdin      = scala.io.Source.stdin
    // a bit obscure, but so concise ! Since stdin is pseudo-lazy (it has an internal buffer for efficient IO), the
    // takeWhile statement won't actually collect the characters in memory and will only calculate their sum. The same
    // statement will consume all digits until it sees a space, and will consume this space as well. The rest of the
    // expression will hold whatever is left in memory (it's ok because it's much smaller) and parse it as an Int.
    val initialSum = stdin.takeWhile(_ != ' ').map(_ - '0').sum.toLong * stdin.mkString.toLong

    @tailrec
    def sumDigits(n: Long, acc: Long = 0): Long = n match {
      case 0 => acc
      case _ => sumDigits(n / 10, (n % 10) + acc)
    }

    @tailrec def recSol(n: Long): Long = if (n < 10) n else recSol(sumDigits(n))

    println(recSol(initialSum))
  }
}

