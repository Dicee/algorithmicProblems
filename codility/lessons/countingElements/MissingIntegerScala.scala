package codility.lessons.countingElements

/**
 * Level : respectable
 */
object MissingIntegerScala {
	def solution(A : Array[Int]) : Int = {
		val N = A.length;
		val tab : Array[Boolean] = new Array(N)

		for (a <- A; if (a <= N && a > 0)) tab(a - 1) = true;
		val opt = (1 to N).map(i => i -> tab(i - 1)).find(x => !x._2)
		if (opt.isEmpty) N + 1 else opt.get._1
	}

	def main(args : Array[String]) {
		println(solution(Array(1, 3, 6, 4, 1, 2)))
	}
}
