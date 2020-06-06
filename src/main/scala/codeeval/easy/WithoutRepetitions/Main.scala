package codeeval.easy.WithoutRepetitions;

object Main extends App {
    println(scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
    	.map(line => line.split("\\s+").map(w => {
    	    w.replaceAll("(.)(\\1){1,}","$1")
    	}).mkString(" "))
    	.mkString("\n")
    )
}