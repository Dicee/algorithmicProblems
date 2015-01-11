object Main extends App {
	val alphabet = (for (ch <- Range('a','z' + 1)) yield ch.toChar).toSet
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => {
			val nums = line.split(";")(1).split(",").map(_.toInt)
			val sum  = (nums.length - 2)*(nums.length - 1)/2
			nums.sum - sum
		})
		.foreach(println)
}
