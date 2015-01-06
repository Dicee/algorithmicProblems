object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
    	.map(line => {
    	    var n   = line.toInt
    	    var cp  = n
    	    while (cp != 0) {
    	        n  -= Math.pow(cp % 10,line.length).toInt
    	        cp /= 10
    	    }
    	    if (n == 0) "True" else "False"
    	})
    	.foreach(println)
}