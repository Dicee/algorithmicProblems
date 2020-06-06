package codeeval.easy.ComparePoints

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0)).getLines.map(_.split(" ").map(_.toInt)).map {
        case Array(x0, y0, x1, y1) => if (x0 == x1 && y0 == y1) "here" else northOrSouth(y0, y1) + eastOrWest(x0, x1) 
    }.foreach(println)
    
    def eastOrWest  (x0: Int, x1: Int) = if (x0 < x1) "E" else if (x0 > x1) "W" else ""
    def northOrSouth(y0: Int, y1: Int) = if (y0 < y1) "N" else if (y0 > y1) "S" else ""
}