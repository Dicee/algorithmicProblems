package projectEuler.pb_16_to_30

import java.util.Arrays

import scala.annotation.tailrec
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
	
	def problem19 = {
		val year   : Int => Int        = { case n => 365 + (if ((n % 4 == 0 && n % 100 != 0) || n % 400 == 0) 1 else 0) }
		val months : Int => Array[Int] = { case n => Array(31,28 + year(n),31,30,31,30,31,31,30,31,30,31) }
		lazy val fm : Stream[Int] = (year(1900) % 7) #:: fm.zipWithIndex
				.map(p => {
					var m = months(1900 + p._2 / 12)
					(p._1 + m(p._2 % 12)) % 7
				})
		fm.take(1200).count(_ == 6)
	}
	
	def problem20 = {
		def fact(n : Int) = {
			@tailrec
			def factRec(n : Int, acc : BigInt) : BigInt = if (n < 2) acc else factRec(n-1,acc*n)
			factRec(n,1)
		}
		fact(100).toString.map(_.asDigit).sum
	}
	
	def problem21 = {
		def d(n : Int) = (1 to n / 2).filter(n % _ == 0).sum
		(1 to 10000).filter(n => { val a = d(n); a != n && d(a) == n }).sum
	}
	
	def problem22 = {
		var s = Source.fromURL(getClass.getResource("data/problem22.txt")).mkString
		s     = s.substring(1,s.length - 1)
		s.split("\",\"").sortWith(_ < _).zipWithIndex.map(x => x._1.map(_ - 'A' + 1).sum * (x._2 + 1L)).sum
	}
	
	def problem23 = {
		val abundant = (1 to 28123).filter(n => (1 to n / 2).filter(n % _ == 0).sum > n)
		val diff     = abundant.flatMap(a => abundant.takeWhile(_ <= 28123 - a).map(a + _))
		((1 to 28123) diff diff).sum
	}
	
	def problem25 = {
		lazy val fibo : Stream[BigInt] = 1 #:: fibo.zip(0 #:: fibo).map(p => p._1 + p._2)
		fibo.takeWhile(x => x.toString.length < 1000).zipWithIndex.last._2 + 2
	}
	
	def main(args : Array[String]) {
		println(problem25)
	}
}