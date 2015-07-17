package codeeval.easy.CapitalizeWords;

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => { 
    	    val capitalize = (w: String) => w.zipWithIndex.map { case (ch,i) => if (i == 0) ch.toUpper else ch }.mkString
    	    println(line.split("\\s+").map(capitalize).mkString(" "))
    	})
}