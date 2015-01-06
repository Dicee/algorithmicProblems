object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => {
    		val map = new scala.collection.mutable.HashMap[Char,Int]
    	    line.filter(_.isLetter).foreach(ch => map += (ch.toLower -> (map.get(ch.toLower) match { case Some(x) => x + 1; case None => 1 })))
    	    println(map.values.toArray.sortWith((a,b) => a > b).zipWithIndex.map { case (x,i) => x * (26 - i) }.sum)
    	})
}