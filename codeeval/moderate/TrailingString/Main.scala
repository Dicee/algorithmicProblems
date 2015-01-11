object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => {
			val split = line.split(",")
			if (split(0).endsWith(split(1))) 1 else 0
		})
		.foreach(println)
}
