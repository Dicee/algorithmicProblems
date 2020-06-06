package codeeval.easy.NiceAngles;

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
        .map(line => {
        	val i           = line.indexOf(".")
        	val decimalPart = line.substring(i).toDouble
        	val mins        = (decimalPart*60).floor.toInt
        	var secs        = ((decimalPart - mins/60d)*36).toString
        	secs            = secs.substring(secs.indexOf(".") + 1).take(2)
        	if (secs.length == 1) secs = "0" + secs
        	
        	"%s.%s'%s\"".format(line.substring(0,i),
        	        			(if (mins.toString.length == 1) "0" else "") + mins,
        	        			(if (secs.length == 1) "0" else "") + secs)
        })
        .foreach(println)
}