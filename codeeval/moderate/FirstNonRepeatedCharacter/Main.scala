object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
        	val nonRepeated = line.groupBy(identity).mapValues(_.length).filter{ case (ch,count) => count == 1 }.keys.toSet
        	line.find(nonRepeated contains _).get
        })
        .foreach(println)
}