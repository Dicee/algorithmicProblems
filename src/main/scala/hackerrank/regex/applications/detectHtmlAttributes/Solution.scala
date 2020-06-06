package hackerrank.regex.applications.detectHtmlAttributes

import scala.util.matching.Regex

// Difficulty: easy

// https://www.hackerrank.com/challenges/html-attributes
object Solution {
  // would produce a false positive if a tag was in a string
  private val TagRegex = new Regex("<(\\w+)\\b.*?>")
  private val AttributeRegex = new Regex("(\\w+?)\\s*=\\s*(\"|').*?\\2")

  def main(args: Array[String]): Unit = {
    val htmlSnippet = scala.io.Source.stdin.getLines().mkString(" ")
    val solution = TagRegex.findAllMatchIn(htmlSnippet)
      .map(matcher => (matcher.group(1), findAttributes(matcher.group(0)))).toSeq
      .groupBy(_._1)
      .mapValues(attributes => attributes.flatMap(_._2).distinct.sorted).toSeq
      .sortBy(_._1)
      .map { case (tag, attributes) => tag + ":" + attributes.mkString(",")}
      .mkString("\n")

    println(solution)
  }

  private def findAttributes(attributesList: String) = AttributeRegex.findAllMatchIn(attributesList).map(_.group(1)).toSeq
}
