object Main extends App {
	def countOnes(n: Int) = {
		var (j,count) = (n,0)
		while (j > 0) { count += j % 2; j /= 2 }
		count
	}
	
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => countOnes(line.toInt))
		.foreach(println)
}
