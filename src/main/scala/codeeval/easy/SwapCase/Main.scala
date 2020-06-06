package codeeval.easy.SwapCase;

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => println(line.map(ch => if (ch.isLower) ch.toUpper else ch.toLower)))
}