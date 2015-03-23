object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
		.map(line => if (line.map(_.toInt - '0').map(Math.pow(_,line.length).toInt).sum == line.toInt) "True" else "False")
		.foreach(println)
}