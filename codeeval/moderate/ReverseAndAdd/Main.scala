object Main extends App {
	def isPalindrome(n: Int) = reverse(n) == n
	def reverse(n: Int) = {
		var (j,reversed) = (n,0)
		while (j > 0) {
			reversed = reversed*10 + j % 10
			j /= 10
		}
		reversed
	}
	
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(_.toInt)
		.foreach(n => {
			val f      = (x: Int) => x + reverse(x)
			val res = Stream.iterate[Int](n)(f).zipWithIndex.find { case (x,i) => isPalindrome(x) }.get
			println(res._2 + " " + res._1)
		})
}
