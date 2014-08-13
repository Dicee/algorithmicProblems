package projectEuler.pb_16_to_30

import java.util.Arrays
import scala.io.Source

object Problems_16_to_30_Scala {
	def problem17 = {
		val units = Array("","one","two","three","four","five","six","seven","eight","nine","ten",
			"eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen").map(_.length)
		val tens  = Array("","","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety").map(_.length)
		(1 to 1000).map(p => { 
			if (p == 1000) 11
			else {
				var u   = p % 10
				var t   = ((p - u)/10) % 10
				var h   = (p - u - t*10)/100
				var hun = (if (h > 0) 7 else 0) + (if (h > 0 && t + u > 0) 3 else 0)
				units(h) + hun + (if (t < 2) units(10*t + u) else tens(t) + units(u))
			}
		}).sum
	}
	
	/*def problem18 = {
		var field : Array[Array[Float]] = Array()
		for (line <- Source.fromURL(getClass.getResource("data/problem18.txt")).getLines)
			field = field ++: line.split("\\s+").map(x => 1/x.toFloat)  
		println(field)
	}*/
	
	def main(args : Array[String]) {
//		println(problem18);
	}
}