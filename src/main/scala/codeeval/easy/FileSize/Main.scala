package codeeval.easy.FileSize;

import scala.io.Source;

object Main extends App {
    println(Source.fromFile(args(0)).getLines.map(_.length + 1).sum)
}