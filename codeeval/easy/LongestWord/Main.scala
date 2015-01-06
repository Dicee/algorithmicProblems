object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => line.split("\\s+")maxBy(_.length))
    	.foreach(println)
}