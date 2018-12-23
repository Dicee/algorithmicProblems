package interviewbit.Programming.DynamicProgramming.WordBreakII

import scala.collection.immutable.TreeMap

// Difficulty: easy/medium. The DP part is equivalent to the classic coins problem, the only added difficulty
//             is to collect all possible paths, which is easily done with a graph connecting super-solutions to
//             sub-solutions.

// https://www.interviewbit.com/problems/word-break-ii/
object Solution {
  def main(args: Array[String]): Unit = {
    println(wordBreak("catsanddog", Array("cat", "cats", "and", "sand", "dog")).toList) // List(cats and dog, cat sand dog)
    println(wordBreak("catisanddog", Array("cat", "cats", "and", "sand", "dog")).toList) // List()
    println(wordBreak("abc", Array("a", "b", "c", "ab", "abc")).toList) // List(a b c, ab c, abc)
  }

  def wordBreak(s: String, words: Array[String]): Array[String]  = {
    val uniqueWords = words.view.distinct.sorted.toArray
    val dp = Array.ofDim[Boolean](s.length + 1)
    val sentences = Array.fill[Sentences](s.length + 1)(new Sentences)
    dp(0) = true

    for (i <- 1 to s.length) {
      for (word <- uniqueWords) {
        val j = i - word.length
        val isSuitable = j >= 0 && dp(j) && s.substring(j, i) == word
        if (isSuitable) sentences(i).addSentences(word, sentences(j))
        dp(i) |= isSuitable
      }
    }
    sentences.last.generateAll()
  }

  private class Sentences {
    private var reverseTransitions = TreeMap[String, Sentences]()

    def addSentences(word: String, sentences: Sentences): Unit = reverseTransitions = reverseTransitions + (word -> sentences)

    def generateAll(): Array[String] =
      if (reverseTransitions.isEmpty) Array()
      else generateSentences(List(Nil)).view.map(_.mkString(" ")).sorted.toArray

    private def generateSentences(acc: List[List[String]]): List[List[String]] = {
      if (reverseTransitions.isEmpty) acc
      else reverseTransitions.view.flatMap { case (word, sentences) => sentences.generateSentences(acc.map(sentence => word :: sentence)) }.toList
    }
  }
}