object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
        .foreach(line => {
        	val split      = line.split("\\s+")
        	var (low,high) = (0,split(0).toInt)
        	var mid        = (low + high + 1)/2
        	for (i <- Range(1,split.length)) { 
        	    split(i) match {
        	    	case "Lower"  => high = mid - 1 
        	    	case "Higher" => low  = mid + 1
        	    	case _        => println(mid)
        	    }
        	    mid = (low + high + 1)/2
        	}
        })
}