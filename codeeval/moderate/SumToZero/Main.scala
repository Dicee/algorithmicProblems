object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val split = line.split(",").map(_.toInt)
            val len   = split.length
            (for (i <- Range(0,len) ; j <- Range(i+1,len) ; k <- Range(j+1,len) ; l <- Range(k+1,len)) yield 
            	split(i) + split(j) + split(k) + split(l)).filter(_ == 0).length
        })
        .foreach(println)
}