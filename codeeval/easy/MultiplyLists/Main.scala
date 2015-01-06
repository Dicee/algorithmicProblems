object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    line.split("\\s+\\|\\s+")
    	    	.map(x => x.split("\\s+")
    	    		.map(_.toInt))
    	    	    .reduce((a,b) => a.zip(b).map { case (x,y) => x*y })
    	    	     .mkString(" ")
    	})
    	.foreach(println)
}