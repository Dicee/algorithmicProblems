package interviewbit.Programming.Strings.JustifiedText

import scala.collection.mutable
import scala.math._

// Difficulty: not too hard

// https://www.interviewbit.com/problems/justified-text/
object Solution {
  def main(args: Array[String]): Unit = {
    println(fullJustify(Array("This", "is", "an", "example", "of", "text", "justification."), 16).mkString("\n").replace(' ', '+'))
    /*
        This++++is++++an
        example++of+text
        justification.++
    */
    println(fullJustify(Array("What", "must", "be", "shall", "be."), 12).mkString("\n").replace(' ', '+'))
    /*
        What+must+be
        shall be.+++
    */
  }

  def fullJustify(words: Array[String], desiredWidth: Int) = {
    val justifiedLines = mutable.ArrayBuffer[String]()
    var i  = 0

    while (i < words.length) {
      var actualWidth = words(i).length
      var j = i

      while (j < words.length - 1 && (actualWidth + words(j + 1).length + j + 1 - i) <= desiredWidth) {
        actualWidth += words(j + 1).length
        j += 1
      }

      justifiedLines += makeLine(words, i, j, desiredWidth, actualWidth)
      i = j + 1
    }

    justifiedLines.toArray
  }

  private def makeLine(words: Array[String], i: Int, j: Int, desiredWidth: Int, actualWidth: Int): String = {
    if (j == words.length - 1) words.view.slice(i, j + 1).mkString(" ").padTo(desiredWidth, " ").mkString("")
    else {
      val sb = new StringBuilder

      val numPaddings        = max(j - i, 1)
      val spacesBetweenWords = " " * ((desiredWidth - actualWidth) / numPaddings)
      var remainingSpaces    = desiredWidth - actualWidth - spacesBetweenWords.length * numPaddings

      for (k <- i to j) {
        sb.append(words(k))
        if (k < j || i == j) sb.append(spacesBetweenWords)
        if (remainingSpaces > 0) {
          sb.append(' ')
          remainingSpaces -= 1
        }
      }

      sb.toString()
    }
  }
}
