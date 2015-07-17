package codeeval.easy.ReadMore;

import scala.io.Source;

object Main extends App {
    for (line <- Source.fromFile(args(0)).getLines) 
        println(
            if (line.length > 55) {
            	var s = line.substring(0,40) 
            	var i = s.lastIndexOf(' ')
            	(if (i >= 0) s.substring(0,i) else s) +"... <Read More>"  
            } else line
        )
}