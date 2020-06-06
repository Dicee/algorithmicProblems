package codeeval.easy.Lowercase;

import scala.io.Source

object Main extends App {
    // for small files
    // println(Source.fromFile(args(0)).mkString.map(_.toLower))

    // for larger files, reduce the memory used
    for (line <- Source.fromFile(args(0)).getLines)
        println(line.toLowerCase);
}