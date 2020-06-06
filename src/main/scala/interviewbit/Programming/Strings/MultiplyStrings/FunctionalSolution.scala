package interviewbit.Programming.Strings.MultiplyStrings

import scala.annotation.tailrec

// Difficulty: easy, but more tedious to write than I would have thought. Also, my solution is definitely less efficient
//             than what real BigIntegers do, in particular the functional one due to all the list reversals. My iterative
//             version must be a bit faster as it leverages Java's ArrayDeque (no equivalent in Scala) to add/remove from
//             both ends efficiently.

// https://www.interviewbit.com/problems/multiply-strings/
object FunctionalSolution {
  def main(args: Array[String]): Unit = {
    println(multiply("10", "12")) // 120
    println(multiply("40", "25")) // 1000
    println(multiply("1958", "12984729")) // 25424099382
    println(multiply("0", "12984729")) // 0
  }

  def multiply(s1: String, s2: String): String = {
    def addDigits    (ch1: Char, ch2        : Char, carry: Int) = withCarry(_ + _, ch1.asDigit, ch2.asDigit, carry)
    def multiplyDigit(ch : Char, digitFactor: Int , carry: Int) = withCarry(_ * _, ch .asDigit, digitFactor, carry)
    def withCarry(op: (Int, Int) => Int, a: Int, b: Int, carry: Int) = {
      val p = op(a, b) + carry
      (p % 10, p / 10)
    }

    @tailrec
    def addStrings(l1: List[Char], l2: List[Char], carry: Int = 0, acc: List[Char] = Nil): List[Char] = (l1, l2) match {
      case (h1 :: t1, h2 :: t2) =>
        val (digit, newCarry) = addDigits(h1, h2, carry)
        addStrings(t1, t2, newCarry, (digit + '0').toChar :: acc)

      case (Nil   , _ :: _) => addStrings(l2, l1        , carry, acc)
      case (_ :: _,  Nil  ) => addStrings(l1, '0' :: Nil, carry, acc)
      case (Nil   , Nil   ) => if (carry > 0) (carry + '0').toChar :: acc else acc
    }

    @tailrec
    def multiplyString(l: List[Char], digitFactor: Int, carry: Int = 0, acc: List[Char] = Nil): List[Char] = l match {
      case h :: t =>
        val (digit, newCarry) = multiplyDigit(h, digitFactor, carry)
        multiplyString(t, digitFactor, newCarry, (digit + '0').toChar :: acc)

      case Nil => if (carry > 0) (carry + '0').toChar :: acc else acc
    }

    @tailrec
    def multiplyStrings(l1: List[Char], l2: List[Char], sum: List[Char] = Nil, suffix: List[Char] = Nil): String = l2 match {
      case h :: t =>
        val product = multiplyString(l1, h.asDigit, acc = suffix)
        multiplyStrings(l1, t, addStrings(sum.reverse, product.reverse), '0' :: suffix)

      case Nil => sum.mkString("")
    }

    val result = multiplyStrings(s1.view.reverse.toList, s2.view.reverse.toList)
    "^(0)*?(?<=0)([1-9]|0$)".r.replaceAllIn(result, "$2")
  }
}
