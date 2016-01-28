package codeeval.easy.CleanUpTheWords

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0)).getLines.map(_.replaceAll("[^a-zA-Z]+", " ").toLowerCase).foreach(println)
}