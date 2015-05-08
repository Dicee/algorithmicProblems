package codeeval.moderate.PascalsTriangle;

object Main extends App {
	def pascalSeries(n: Int, sb: StringBuilder): List[Int] = {
		if (n == 1)	{ 
			sb.append("1 ")
			List(1) 
		} else {
			val series = pascalSeries(n-1,sb)
			val res    = (series :+ 0) zip (0 :: series) map { case (a,b) => a + b }
			res.addString(sb,""," "," ")
			res
		}
	}
	
	scala.io.Source.fromFile(args(0)).getLines
		.map(line => {
			val sb     = new StringBuilder()
			pascalSeries(line.toInt,sb)
			sb
		})
		.foreach(println)
}
