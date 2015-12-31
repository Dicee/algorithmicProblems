package codeeval.moderate.PascalsTriangle;

import scala.io.Source

object Main extends App {
	def pascalSeries(n: Int, sb: StringBuilder): List[Int] = n match {
        case 1 => sb.append("1 "); List(1)
        case _ => 
            val series = pascalSeries(n - 1, sb)
            val res    = (series :+ 0) zip (0 :: series) map { case (a, b) => a + b }
            res.addString(sb, "", " ", " ")
            res
    }
	Source.fromFile(args(0)).getLines.map(line => { val sb = new StringBuilder(); pascalSeries(line.toInt, sb); sb }).foreach(println)
}
