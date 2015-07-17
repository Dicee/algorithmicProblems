package codeeval.easy.FindWriter;

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => {
    	    val split = line.split("\\|")
    	    val sb    = new StringBuilder
    	    split(1).split("\\s+").filter(!_.isEmpty).map(_.toInt).foreach(i => sb.append(split(0).charAt(i - 1)))
    	    println(sb)
    	})
}