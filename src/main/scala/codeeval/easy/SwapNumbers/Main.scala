package codeeval.easy.SwapNumbers

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0)).getLines.map(_.split(" ").map(_.toArray))
          .map(words => words.map(chars => { swap(chars, 0, chars.length - 1) ; chars }))
          .map(words => words.map(new String(_)).mkString(" "))
          .foreach(println)
    
    def swap(arr: Array[Char], i: Int, j: Int) = { 
        val tmp = arr(i)
        arr(i)  = arr(j)
        arr(j)  = tmp
    }
}