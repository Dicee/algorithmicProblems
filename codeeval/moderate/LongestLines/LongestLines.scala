import scala.collection.mutable.PriorityQueue;

object Main extends App {
    val order = new Ordering[String] { 
        def compare(s1: String,s2: String) = Integer.compare(s2.length,s1.length)
    }
    val queue = new PriorityQueue[String]()(order)
    
    var n = 0
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => 
    	    if (n == 0)
    	        n = line.toInt
    	    else {
    	        if (queue.length == n) order.compare(queue.head,line) match {
    	            case x if x <  0 => 
    	            case x if x >= 0 => queue.dequeue; queue += line
    	        } else
    	            queue += line
    	    }
    	)
    	val res = Array.ofDim[String](queue.length)
    	var i   = queue.length - 1
    	while (!queue.isEmpty) { res(i) = queue.dequeue; i -= 1 }
    	res.foreach(println)
}