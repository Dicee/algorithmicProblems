package projectEuler.pb_31_to_45

import projectEuler.utils.ExtendedMath._
import java.util.Arrays
import projectEuler.utils.PermutationGenerator
import scala.io.Source
import projectEuler.utils.PermGen

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
		(2 to 999999).count(n => eligible(n) && nextList(n.toString,next(n.toString)).map(_.toInt).forall(isPrime(_)))
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
	
	def problem42 = {
		def isTriangle(n : Int) = ((Math.sqrt(1 + 8*n) - 1)/2 % 1) == 0
		var s = Source.fromURL(getClass.getResource("data/problem42.txt")).mkString
		s.split(",").map(w => w.map(ch => if (!ch.isLetter) 0 else ch - 'A' + 1).sum).count(isTriangle)
	}
	
	def problem43 = {
		def subNum(perm : Array[Int], index : Int) = 100*perm(index) + 10*perm(index + 1) + perm(index + 2)
		PermGen("1023465789").filter(perm =>
			subNum(perm,1) % 2 == 0 &&
			subNum(perm,2) % 3 == 0 &&
			subNum(perm,3) % 5 == 0 &&
			subNum(perm,4) % 7 == 0 &&
			subNum(perm,5) % 11 == 0 &&
			subNum(perm,6) % 13 == 0 &&
			subNum(perm,7) % 17 == 0).map(perm => perm.mkString.toLong).sum
	}
	
	def main(args : Array[String]) {
		println(problem43)
//		for (a <- PermGen("210",true)) println(a.mkString)
//		println(nextList(100,next(100)).forall(isPrime))
	}
}