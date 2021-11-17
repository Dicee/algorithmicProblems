package codeeval.hard.TypeAhead

import scala.collection.mutable.{ HashMap => MHashMap, Map => MMap }

object Main extends App {
  val rawText = "Mary had a little lamb its fleece was white as snow;\nAnd everywhere that Mary went, the lamb was sure to go.\nIt followed her to school one day, which was against the rule;\nIt made the children laugh and play, to see a lamb at school.\nAnd so the teacher turned it out, but still it lingered near,\nAnd waited patiently about till Mary did appear.\n\"Why does the lamb love Mary so?\" the eager children cry; \"Why, Mary loves the lamb, you know\" the teacher did reply.\""
  val text    = rawText.replaceAll("[^\\w ]|_", " ").replaceAll("\\s+", " ")
  val words   = text.split(' ')

  val memoizedResults = MHashMap[(Int, String), MMap[String, Int]]()

  scala.io.Source.fromFile(args(0)).getLines()
    .map(_.split(',') match { case Array(n, ngram) => (n.toInt, ngram.split(' ')) })
    .map {
      case (n, ngram) =>
        val ngramString = ngram.mkString

        memoizedResults.getOrElseUpdate((n, ngramString), {
          val occurrences = MHashMap[String, Int]()

          words.zipWithIndex.sliding(n - 1).foreach {
            case indexedSlice =>
              if (indexedSlice.map(_._1) sameElements ngram) {
                val key      = words(indexedSlice.last._2 + 1)
                occurrences += key -> (occurrences.getOrElse(key, 0) + 1)
              }
          }

          occurrences
        })
    }
    .map(_.toArray.sortWith { case ((word1, count1), (word2, count2)) => (count1 == count2 && word1 < word2) || count1 > count2 })
    .map(occurrences => {
      val total = occurrences.map(_._2).sum.toDouble
      occurrences.map { case (word, count) => (word, count / total) }
    })
    .map(_.map { case (word, freq) => word + "," + freq.formatted("%.3f").replace(',', '.') }.mkString(";"))
    .foreach(println)
}
