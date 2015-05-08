package codeeval.easy.JugglingWithZeroes;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    val sb   = new StringBuilder
    	    var kind = ""
    	    for (x <- line.split("\\s+").zipWithIndex) x match {
    	        case (s,i) =>
    	            if (i % 2 == 1) sb.append(kind*s.length)
    	            else  	        kind = if (s == "0") "0" else "1"
    	    }
    	    java.lang.Long.parseLong(sb.toString,2)
    	})
    	.foreach(println)
}