object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => line.split("\\s+").map(_.toDouble).sorted.map("%.3f".format(_)).mkString(" "))
    	.foreach(println)
}