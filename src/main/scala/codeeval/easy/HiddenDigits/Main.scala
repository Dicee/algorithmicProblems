package codeeval.easy.HiddenDigits;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    line.map {
    	        case x if x.isDigit                    => x
    	        case x if x - 'a' <= 9 && 0 <= x - 'a' => ('0' + x - 'a').toChar
    	        case _                                 => '%'
    	    }.mkString.replaceAll("%","") match {
    	        case x if x.isEmpty => "NONE"
    	        case x              => x
    	    }
    	})
    	.foreach(println)
}