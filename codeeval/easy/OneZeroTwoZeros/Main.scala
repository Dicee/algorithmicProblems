package codeeval.easy.OneZeroTwoZeros

import scala.io.Source

object Main extends App {
    // Brute force. I know, not very elegant but well, the problem is of very small size.
    Source.fromFile(args(0))
          .getLines
          .map(_.split(" ").map(_.toInt))
          .map { case Array(m, n) => (1 to n).map(countZeros).count(_ == m) }
          .foreach(println)
        
    def countZeros(n: Int) = {
        var (mask, zeros) = (1, 0)
        while (mask <= n) {
            zeros += (if ((mask & n) > 0) 0 else 1)
            mask <<= 1
        }
        zeros
    }
}