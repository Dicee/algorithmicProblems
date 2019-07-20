package hackerrank.algorithms.implementation.encryption

import scala.math._

// Difficulty: easy

// https://www.hackerrank.com/challenges/encryption/problem
object Solution {
  def encryption(raw: String) = {
    val toEncrypt = raw.replaceAll("\\s+", "")
    val (n, m)    = determineGridSize(toEncrypt)
    (0 until m).view.map(j => buildColumn(j, toEncrypt, n, m)).mkString(" ")
  }

  private def determineGridSize(toEncrypt: String) = {
    val sqrtLen        = sqrt(toEncrypt.length)
    val (lower, upper) = (floor(sqrtLen).toInt, ceil(sqrtLen).toInt)

    if      (lower == upper)                    (lower, lower)
    else if (lower * upper >= toEncrypt.length) (lower, upper)
    else                                        (upper, upper)
  }

  private def buildColumn(j: Int, toEncrypt: String, n: Int, m: Int) = {
    val lastLineLength = m - n * m + toEncrypt.length
    val lastLine = if (lastLineLength < m && lastLineLength <= j) n - 1 else n
    (0 until lastLine).map(i => toEncrypt(j + m * i)).mkString
  }
}
