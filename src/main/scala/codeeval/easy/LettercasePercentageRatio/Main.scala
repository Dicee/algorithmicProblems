package codeeval.easy.LettercasePercentageRatio;

import scala.collection.mutable.PriorityQueue;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    var (lower,upper) = (0.0,0.0)
    	    line.foreach(ch => if (ch.isLower) lower += 1 else upper += 1)
    	    "lowercase: %.2f uppercase: %.2f".format(lower/line.length*100,upper/line.length*100)
    	})
    	.foreach(println)
}