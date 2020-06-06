package codeeval.moderate.CountingPrimes;

object Main extends App {
	val queries = scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty).map(_.split(",").map(_.toInt)).toSeq
	val max     = queries.map(x => x(1)).max
	val sieve   = Array.fill[Boolean](max + 1)(true)
	sieve(0)    = false
	sieve(1)    = false
	
	for (i <- Range(2,sieve.length) ; if sieve(i) ; j <- 2*i to sieve.length - 1 by i) sieve(j) = false
	for (q <- queries) println(sieve.slice(q(0),q(1) + 1).count(identity))
}