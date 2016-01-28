package codeeval.easy.FindTheHighestScore

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0))
          .getLines
          .map(_.split(" \\| ").map(_.split(' ').map(_.toInt)))
          .map(rows => {
              val maxPerCol = Array.fill[Int](rows(0).length)(Int.MinValue)
              for (row <- rows ; i <- 0 until row.length) maxPerCol(i) = Math.max(row(i), maxPerCol(i))
              maxPerCol.mkString(" ")
          })
          .foreach(println)
}