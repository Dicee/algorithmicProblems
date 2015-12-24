package codeeval.moderate.MinimumCoins

import scala.io.Source

object Main extends App {
    val COINS = List(5, 3, 1)
    Source.fromFile(args(0))
          .getLines
          .map(_.toInt)
          .map(x => { 
              var (total, remaining) = (0, x)
              for (coin <- COINS) {
                  total    += remaining / coin
                  remaining = remaining % coin
              }
              total
          })
         .foreach(println)
}