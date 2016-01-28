package codeeval.easy.NotSoClever

import scala.io.Source

object Main extends App {
    def iterateStupidSort(arr: Array[Int], it: Int): Array[Int] = {
        var sortedUpTo = 0
        for (i <- 1 to it) {
            while (sortedUpTo < arr.length - 1 && arr(sortedUpTo) <= arr(sortedUpTo + 1)) sortedUpTo += 1
            if (sortedUpTo == arr.length - 1) return arr
            
            val tmp             = arr(sortedUpTo)
            arr(sortedUpTo)     = arr(sortedUpTo + 1)
            arr(sortedUpTo + 1) = tmp

            if (sortedUpTo > 0) sortedUpTo -= 1
        }
        arr
    }
    
    Source.fromFile(args(0))
          .getLines
          .map(_.split("\\s+\\|\\s+"))
          .map { case Array(left, right) => (left.split("\\s+").map(_.toInt), right.toInt) }
          .map { case (arr, it) => iterateStupidSort(arr, it).mkString(" ") }
          .foreach(println)
}