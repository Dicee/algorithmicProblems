package codeeval.hard.TelephoneWords

import scala.collection.mutable.ListBuffer

object Main extends App {
  val phoneKeyToValues = Map(
    '0' -> "0",
    '1' -> "1",
    '2' -> "abc",
    '3' -> "def",
    '4' -> "ghi",
    '5' -> "jkl",
    '6' -> "mno",
    '7' -> "pqrs",
    '8' -> "tuv",
    '9' -> "wxyz").mapValues(_.toList)

  // sacrifice some memory for getting more speed. Indeed, writing each word to the standard output would be much slower than a single
  // concatenation of all the words
  def depthFirstSearch(inputs: List[List[Char]], currentWord: ListBuffer[Char] = ListBuffer(), depth: Int = 0): ListBuffer[String] = inputs match {
    case                  Nil => if (currentWord.isEmpty) ListBuffer() else ListBuffer(currentWord.mkString)
    case Nil           :: _   => ListBuffer()
    case (ch :: chars) :: q   =>
      currentWord += ch

      val wordsWithSamePrefix = depthFirstSearch(q, currentWord, depth + 1)

      // it kills me that I have to do this removal in O(n) but Scala provides no implementation of double linked list...
      // fortunately, the problem statement limits the size of this list to 7, but still...
      currentWord.remove(depth)
      wordsWithSamePrefix ++= depthFirstSearch(chars :: q, currentWord, depth)
  }

  scala.io.Source.fromFile(args(0)).getLines()
    .map(_.map(phoneKeyToValues).toList)
    .map(depthFirstSearch(_))
    .foreach(sol => println(sol.mkString(",")))
}
