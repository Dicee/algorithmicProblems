package codeeval.easy.KnightMoves

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0))
          .getLines
          .map(_.toArray)
          .map { case Array(col, row) => (col - 'a' + 1, row - '0') }
          .map { case (x, y) => for {
                  i <- List(-2, -1, 1, 2);
                  j <- List(-2, -1, 1, 2);
                  if Math.abs(i) != Math.abs(j);
                  if isBetween(1, i + x, 9) && isBetween(1, j + y, 9)
              } yield (i + x, j + y)
          }
          .map(_.map{ case (x, y) => s"${('a' + x - 1).toChar}${('0' + y).toChar}" }.mkString(" "))
          .foreach(println)

    def isBetween(lower: Int, n: Int, upper: Int) = lower <= n && n < upper
}