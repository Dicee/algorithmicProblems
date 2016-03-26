package codeeval.easy.MersennePrime

object Main extends App {
    val max             = 3000
    val primes          = eSieve(max).zipWithIndex.filter(_._1).map(_._2)
    val mersenneNumbers = primes.map(p => Math.pow(2, p).toInt - 1).filter(_ < max)

    scala.io.Source.fromFile(args(0))
                   .getLines()
                   .map(s => mersenneNumbers.takeWhile(_ < s.toInt).mkString(", "))
                   .foreach(println)

    private def eSieve(n: Int) = {
        val sieve = Array.fill[Boolean](n + 1)(true)
        sieve(0) = false
        sieve(1) = false

        Stream.range(2, n).filter(sieve(_)).foreach(i => Stream.iterate(2 * i)(_ + i).takeWhile(_ < n).foreach(sieve(_) = false))
        sieve
    }
}
