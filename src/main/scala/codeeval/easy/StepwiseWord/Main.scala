package codeeval.easy.StepwiseWord

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0))
          .getLines
          .map(_.split(" ").toList)
          .map(words => { val maxLength = words.map(_.length).max; words.find(_.length == maxLength).get })
          .map(word => word.zipWithIndex.map { case (ch, i) => ("*" * i) + ch }.mkString(" "))
          .foreach(println)
}