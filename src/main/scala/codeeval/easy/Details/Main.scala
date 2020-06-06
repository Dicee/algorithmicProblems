package codeeval.easy.Details;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
		.map(_.split(",").map(s => s.indexOf('Y') - s.lastIndexOf('X') - 1).min)
		.foreach(println)
}