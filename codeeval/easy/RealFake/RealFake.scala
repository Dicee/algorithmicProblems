package codeeval.easy.RealFake

import scala.io.Source

object Main extends App {
    val source =
        Source.fromFile(args(0))
            .getLines
            .map(_.replace("\\s+", "").map(_.toInt).zipWithIndex)
            .map(_.map { case (x, i) => if (i % 2 == 0) 2 * x else x })
            .map(_.sum)
            .foreach(println)
}
