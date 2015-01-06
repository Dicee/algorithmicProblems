object Main extends App {
	val board = Array.ofDim[Int](256,256)
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => {
    	    val split = line.split("\\s+")
    	    val (i,j) = (split(1).toInt,if (split.length == 3) Some(split(2).toInt) else None)
    	    split(0) match {
    	        case "SetRow"   => for (k <- 0 to 255) board(i)(k) = j.get
    	        case "SetCol"   => for (k <- 0 to 255) board(k)(i) = j.get
    	        case "QueryRow" => println(board(i).sum)
    	        case "QueryCol" => println((for (k <- 0 to 255) yield board(k)(i)).sum)
    	    }
    	})
}