package hackerrank.regex.applications.smartIDE

import scala.io.Source
import scala.util.matching.Regex

// Difficulty: medium. My strategy was to remove any syntax-free string (strings, comments) and then look for constructs
//             specific to one of the three supported languages until I get a match. It's in no way exhaustive but covers
//             all test cases proposed by HackerRank.

// https://www.hackerrank.com/challenges/programming-language-detection
object Solution {
  private val Python = "Python"
  private val Java = "Java"
  private val C = "C"

  private val PythonDef = new Regex("def\\s+\\w+\\s*\\((\\w,?)*\\)\\s*:")
  // voluntarily redundant with the C include/define regex
  private val PythonComment = new Regex("#(?!define|include)")

  private val CPointer = new Regex("->")
  private val CIncludeOrDefine = new Regex("#(define|include)")

  private val JavaImport = new Regex("import (\\w\\.?)+(\\w|\\*);")

  def main(args: Array[String]): Unit = {
    val lines = Source.stdin.getLines()

    var isOngoingMultiLineComment = false

    while (lines.hasNext) {
      var line = removeStrings(lines.next())

      if (isOngoingMultiLineComment) {
        val multiLineCommentEnd = line.indexOf("*/")
        if (multiLineCommentEnd >= 0) {
          line = line.substring(Math.min(multiLineCommentEnd + 2, line.length - 1))
          isOngoingMultiLineComment = false
        } else line = ""
      }

      line = removeMultiLineCommentsOnSingleLine(line)

      val multiLineCommentStart = line.indexOf("/*")
      if (multiLineCommentStart >= 0) {
        val multiLineCommentEnd = line.indexOf("*/")
        if (multiLineCommentEnd >= 0) {
          val prefix = line.substring(0, multiLineCommentStart)
          val suffix = line.substring(Math.min(multiLineCommentEnd + 2, line.length - 1))
          line = prefix + suffix
        } else {
          isOngoingMultiLineComment = true
          line = line.substring(0, multiLineCommentStart)
        }
      }

      line = removeSingleLineJavaAndCComments(line)

      if (PythonDef.findFirstIn(line).isDefined) answerAndExit(Python)
      if (PythonComment.findFirstIn(line).isDefined) answerAndExit(Python)

      if (CPointer.findFirstIn(line).isDefined) answerAndExit(C)
      if (CIncludeOrDefine.findFirstIn(line).isDefined) answerAndExit(C)

      if (JavaImport.findFirstIn(line).isDefined) answerAndExit(Java)
    }
  }

  private def answerAndExit(ans: String): Unit = {
    println(ans)
    System.exit(0)
  }

  private def removeMultiLineCommentsOnSingleLine(snippet: String) = snippet.replaceAll("/\\*.*?\\*/", "")

  private def removeSingleLineJavaAndCComments(snippet: String) = snippet.replaceAll("//.*$", "")

  private def removeStrings(snippet: String) = snippet.replaceAll("\".*?(?<!\\\\)\"", "amendedstring").trim
}
