package codeeval.easy.MajorElement;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    val split = line.split(",")
    	    split.groupBy(identity).mapValues(_.length).find(kv => kv._2 >= split.length/2) match {
    	        case Some((x,y)) => String.valueOf(x)
    	        case None        => "None"
    	    }
    	})
    	.foreach(println)
}