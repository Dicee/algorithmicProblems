package codeeval.easy.MinimumDistance

import scala.io.Source

object Main extends App {
  val source =
    Source.fromFile(args(0))
          .getLines
          .map(_.split("\\s+").map(_.toInt))
          .map(arr => (arr(0), arr.slice(1, arr(0) + 1).sorted)) 
          .map { case (n, numbers) => 
            val median = if (n % 2 == 0) (numbers(n/2 - 1) + numbers(n/2 + 1)) / 2 else numbers((n - 1)/2) 
            numbers.map(x => Math.abs(x - median)).sum
          }
          .foreach(println)
}
