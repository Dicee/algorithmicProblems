object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
		.map(line => {
			val split      = line.split(";")
			val (days,arr) = (split(0).toInt,split(1).split("\\s+").map(_.toInt).toSeq)
			var max        = arr.take(days).sum
			var sum        = max
			for (i <- days until arr.length) {
				sum += arr(i) - arr(i - days)
				max  = Math.max(max,sum)
			}
			Math.max(max,0)
		})
		.foreach(println)
}