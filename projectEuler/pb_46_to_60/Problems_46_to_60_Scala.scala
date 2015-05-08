package projectEuler.pb_46_to_60

import java.util.ArrayList

object Problems_46_to_60_Scala {
	def problem48 = {
		val n = BigInt(10000000000L);
		val s = (1 to 1000).map(x => BigInt(x).modPow(x,n)).sum.toString
		s.substring(s.length - 10).toLong
	}
	
	def main(args : Array[String]) {
		
//		println(problem48)
//		println(BigInt(1000).modPow(1000,987))
	}
}