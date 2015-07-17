package codeeval.easy.SlangFlavor;

import scala.io.Source;

object Main extends App {
    val replacements = Array(", yeah!",", this is crazy, I tell ya.",", can U believe this?",", eh?",
    		", aw yea.",", yo.","? No way!",". Awesome!")
    		
    var index  = 0
    var change = false
    for (line <- Source.fromFile(args(0)).getLines) {
    	val sb = new StringBuilder
        for (c <- line) {
        	c match {
        		case '.' | '?' | '!' => 
        	    	if (change) {
        	    		sb.append(replacements(index))
        	    		index = (index + 1) % replacements.length
        	    	} else 
        	    	    sb.append(c)
        	    	change = !change;
        		case _ => sb.append(c)
        	}
        }
        println(sb)
    }
}