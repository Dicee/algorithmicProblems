package interviewbit.Programming.Math.SumOfPairwiseHammingDistance

// Difficulty: easy

// https://www.interviewbit.com/problems/sum-of-pairwise-hamming-distance/
object Solution {
  def main(args: Array[String]): Unit = {
    println(hammingDistance(Array(2, 4, 6))) // 8
    println(hammingDistance(Array(3, 4, 6))) // 12
  }

  def hammingDistance(arr: Array[Int]) = {
    val counters = Array.ofDim[Int](Integer.BYTES * java.lang.Byte.SIZE, 2)
    for (v <- arr) {
      var bits = v
      for (i <- counters.indices) {
        counters(i)(bits & 1) += 1
        bits >>= 1
      }
    }
    (2 * counters.map { case Array(zeros, ones) => zeros.toLong * ones.toLong } .sum) % 1000000007
  }
}
