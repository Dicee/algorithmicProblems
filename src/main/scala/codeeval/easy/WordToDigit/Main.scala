package codeeval.easy.WordToDigit;

object Main extends App {
	val map = Map(
	        "zero" -> 0, "one" -> 1, "two"   -> 2, "three" -> 3, "four" -> 4,
	        "five" -> 5, "six" -> 6, "seven" -> 7, "eight" -> 8, "nine" -> 9)
	
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => {
    	    println(line.split(";").map(map).mkString)
    	})
}