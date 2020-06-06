package codeeval.moderate.PrimeNumbers;

object Main extends App {
	val queries = scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty).map(_.toInt).toSeq
	val max     = queries.max
	val sieve   = Array.fill[Boolean](max + 1)(true)
	sieve(0)    = false
	sieve(1)    = false
	
	for (i <- Range(2,sieve.length) ; if sieve(i) ; j <- 2*i to sieve.length - 1 by i) sieve(j) = false
	for (q <- queries) println(sieve.take(q).zipWithIndex.filter { case (x,i) => x }.map{ case (x,i) => i }.mkString(","))	
}
