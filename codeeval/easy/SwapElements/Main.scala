package codeeval.easy.SwapElements;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    val split    = line.split("\\s+:\\s+")
    	    val arr      = split(0).split("\\s+").toArray
    	     
    	    split(1).split("\\s*,\\s*").foreach(s => {
    	        val swap     = s.split("-").map(_.toInt)
    	        val tmp      = arr(swap(0))
    	        arr(swap(0)) = arr(swap(1))
    	        arr(swap(1)) = tmp
    		})
    		arr.mkString(" ")
    	})
    	.foreach(println)
}