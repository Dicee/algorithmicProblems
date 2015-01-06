object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(Integer.parseInt(_,16))
    	.foreach(println)
}