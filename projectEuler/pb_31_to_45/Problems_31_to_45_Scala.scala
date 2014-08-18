package projectEuler.pb_31_to_45

import utils.ExtendedMath_Java._

import java.util.Arrays

object Problems_31_to_45_Scala {
	def problem33 = {
		val frac = for (n <- 1 to 9 ; d <- n + 1 to 9 ; i <- d + 1 to 9 ; a = 10*n + i ; b = 10*i + d if (a*d == b*n)) yield (a,b)
		val prod = frac.reduce((a,b) => (a._1*b._1,a._2*b._2))
		prod._2/(gcd(prod._1,prod._2))
	}
	
	def problem34 = {
		var fact = Array(1,1,1,1,1,1,1,1,1,1)
		for (i <- 1 to 9) fact(i) = i*fact(i - 1)
		val index = Stream.from(1).takeWhile(n => Math.pow(10,n) <= n*fact(9)).last + 1
		(10 to (fact(9) * index).toInt).filter(n => n == n.toString.map(ch => fact(ch.asDigit)).sum).sum
	}
	
	def problem35 = {
		val eligible : Int => Boolean = { 
			case n if (n == 2 || n == 5) => true
			case n                       => n.toString.forall(ch => ch.asDigit % 2 == 1 && ch.asDigit != 5) && isPrime(n)
		}
		def next(s : String) = s.charAt(s.length - 1) + s.substring(0,s.length - 1)
		def nextList(n : String, p : String) : List[String] = if (p == n) Nil else p.toString :: nextList(n,next(p))
		(2 to 999999).filter(n => eligible(n) && nextList(n.toString,next(n.toString)).map(_.toInt).forall(isPrime(_))).length
	}
	
	def problem40 = {
		var sb = new StringBuilder
		var i  = 0;
		while (sb.size < 1000001) {
			sb = sb.append(i)
			i = i + 1
		}
		val s = sb.toString
		Array(s(1),s(10),s(100),s(1000),s(10000),s(100000),s(1000000)).map(_.asDigit).reduce(_*_)
	}
	
	def problem41 = {
//		(1 to 987654321).filter(isPrime(_))
		Stream.from(2).take(10).reverse.toList
	}
	
	def main(args : Array[String]) {
		println(problem41)
//		println(nextList(100,next(100)).forall(isPrime))
	}
}