package interviewbit.Programming.BitManipulation.DifferentBitsSumPairwise

// Difficulty: easy. Exactly the same problem as (https://www.interviewbit.com/problems/sum-of-pairwise-hamming-distance/).

// https://www.interviewbit.com/problems/different-bits-sum-pairwise/
object Solution {
  def cntBits(arr: Array[Int]) = {
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
