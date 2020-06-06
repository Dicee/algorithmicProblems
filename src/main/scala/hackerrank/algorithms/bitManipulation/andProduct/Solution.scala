package hackerrank.algorithms.bitManipulation.andProduct

// Difficulty: fairly easy

// https://www.hackerrank.com/challenges/and-product
object Solution {
  private def reversedBits(n: Long): List[Boolean] = if (n <= 1) List(n == 1) else (n % 2 == 1) :: reversedBits(n >> 1)

  def main(args: Array[String]): Unit = {
    val lines      = scala.io.Source.stdin.getLines()
    lines.next()
    lines.foreach(_.split(' ').map(_.toLong) match {
      case Array(min, max) =>
        val maxBits    = reversedBits(max)
        val bitLength  = maxBits.length
        val minBits    = reversedBits(min).padTo(bitLength, false)

        val commonStrongestBits = minBits.zip(maxBits).reverse.takeWhile { case (minBit, maxBit) => maxBit == minBit }.map(_._1)
        println(commonStrongestBits.zipWithIndex.filter(_._1).map(t => 1L << (bitLength - t._2 - 1)).sum)
    })
  }
}