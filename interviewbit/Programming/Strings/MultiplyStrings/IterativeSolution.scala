package interviewbit.Programming.Strings.MultiplyStrings

import scala.collection.JavaConverters

// Difficulty: easy, but more tedious to write than I would have thought. Also, my solution is definitely less efficient
//             than what real BigIntegers do, in particular the functional one due to all the list reversals. My iterative
//             version must be a bit faster as it leverages Java's ArrayDeque (no equivalent in Scala) to add/remove from
//             both ends efficiently.

// https://www.interviewbit.com/problems/multiply-strings/
object IterativeSolution {
  def main(args: Array[String]): Unit = {
    println(multiply("10", "12")) // 120
    println(multiply("40", "25")) // 1000
    println(multiply("1958", "12984729")) // 25424099382
    println(multiply("0", "12984729")) // 0
  }

  def multiply(s1: String, s2: String): String = {
    type BigNumber = java.util.ArrayDeque[Char]

    var sum = new BigNumber
    val suffix = new BigNumber

    for (j <- s2.indices.view.reverse) {
      val product = new BigNumber(suffix)
      var carry   = 0
      for (i <- s1.indices.view.reverse) {
        val p = s1(i).asDigit * s2(j).asDigit + carry
        product.push(toCharDigit(p % 10))
        carry = p / 10
      }
      if (carry > 0) product.push(toCharDigit(carry))

      sum = add(sum, product)
      suffix.push('0')
    }

    def toCharDigit(value: Int) = (value + '0').toChar
    def add(n1: BigNumber, n2: BigNumber) = {
      val sum = new BigNumber
      var carry = 0

      while (!n1.isEmpty || !n2.isEmpty) {
        val (ch1, ch2) = (if (n1.isEmpty) '0' else n1.removeLast(), if (n2.isEmpty) '0' else n2.removeLast())
        val a = ch1.asDigit + ch2.asDigit + carry
        sum.push(toCharDigit(a % 10))
        carry = a / 10
      }

      if (carry > 0) sum.push(toCharDigit(carry))
      sum
    }

    while (sum.size() > 1 && sum.peek() == '0') sum.pop()
    JavaConverters.collectionAsScalaIterable(sum).mkString("")
  }
}
