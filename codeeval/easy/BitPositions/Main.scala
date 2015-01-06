object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
    	.map(line => {
    		val split = line.split(",").map(_.toInt)
    		val bin   = Integer.toBinaryString(split(0))
    		if (bin(bin.length - split(1)) == bin(bin.length - split(2))) "true" else "false"
    	})
    	.foreach(println)
}