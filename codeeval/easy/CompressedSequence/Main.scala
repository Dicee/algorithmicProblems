object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => {
    	    val split = line.split("\\s+")
    	    var i     = 0
    	    do {
    	    	val curr  = split(i)
    	    	var count = 1
    	    	
    	    	i += 1
    	    	while (i < split.length && curr == split(i)) {
    	    	    i     += 1
    	    	    count += 1
    	    	}
    	    	print("%s%d %s".format(if (i == 0) "" else " ",count,curr)) 
    	    }  while (i < split.length)
    	    println
    	})
}