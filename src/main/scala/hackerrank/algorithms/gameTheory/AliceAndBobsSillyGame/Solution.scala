package hackerrank.algorithms.gameTheory.AliceAndBobsSillyGame

//  Difficulty: easy (marked as medium but very classical)

// https://www.hackerrank.com/challenges/alice-and-bobs-silly-game
object Solution {
  def main(args: Array[String]): Unit = {
    val inputs = scala.io.Source.stdin.getLines().drop(1).map(_.toInt).toSeq
    val maxN = inputs.max
    val sieve = eratostheneSieve(maxN)

    inputs.map(n => {
      val countPrimes = sieve.view.slice(0, n + 1).count(identity)
      if (countPrimes % 2 == 0) "Bob" else "Alice"
    }).foreach(println)
  }

  private def eratostheneSieve(n: Int) = {
    val sieve = Array.fill[Boolean](n + 1)(true)
    sieve(0) = false
    sieve(1) = false

    for (i <- 2 to n; if sieve(i)) {
      Stream.iterate(i + i)(_ + i).takeWhile(_ < sieve.length).foreach(sieve(_) = false)
    }

    sieve
  }
}
