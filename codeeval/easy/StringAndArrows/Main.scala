package codeeval.easy.StringAndArrows

import scala.io.Source

object Main extends App {
    val arrow = "<--<<"
    Source.fromFile(args(0))
          .getLines
          .map(s => countArrows(s) + countArrows(reverseArrows(s)))
          .foreach(println)
    
    def countArrows(s: String) = {
        var (count, i) = (0, 0)
        for (ch <- s) {
            if (ch == arrow(i)) {
                if (i == arrow.length - 1) { 
                    count += 1
                    i      = 1
                } else i += 1
            } 
            else if (i == 4 && ch == '-') i = 2 
            else if (ch == arrow(0))      i = 1
            else                          i = 0
        }
        count
    }
    def reverseArrows(s: String) = s.reverse.replace("<", ".").replace(">", "<").replace(".", ">")
}