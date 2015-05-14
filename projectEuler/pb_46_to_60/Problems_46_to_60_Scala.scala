package projectEuler.pb_46_to_60

import java.util.ArrayList
import scala.math._

object Problems_46_to_60_Scala {
	def problem48 = {
		val n = BigInt(10000000000L);
		val s = (1 to 1000).map(x => BigInt(x).modPow(x,n)).sum.toString
		s.substring(s.length - 10).toLong
	}
	
	def problem53(n: Int) = {
		val million = 1e6.toInt
		def nextCoeffs(coeffs: List[Int], n: Int, nmax: Int, count: Int): Int =
			if (n > nmax + 1) count
			else {
				val next = (coeffs :+ 0).zip(0 +: coeffs).map { case (l,r) => min(million,l + r) }
				nextCoeffs(
					if (n % 2 == 0) min(million,next.head*2) :: next.tail else next.tail,
					n + 1,
					nmax,
					count + max(0,coeffs.count(_ >= million)*2 - (n % 2))
				)
			}
		nextCoeffs(List(1),2,n,0)
	}
	
	def main(args : Array[String]) {
		println(problem53(100))
//		println(problem48)
//		println(BigInt(1000).modPow(1000,987))
	}
}