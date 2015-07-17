package codeeval.moderate.RemoveCharacters;

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => {
			val split = line.split(", ")
			split(0).replaceAll("[" + split(1) + "]","")
		})
		.foreach(println)
}
