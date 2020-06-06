package codeeval.easy.StringMask

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0)).getLines
          .map(_.split(" "))
          .map { case Array(word, mask) => word.zip(mask).map { case (ch, bit) => if (bit == '1') ch.toUpper else ch.toLower }.mkString }
          .foreach(println)
}