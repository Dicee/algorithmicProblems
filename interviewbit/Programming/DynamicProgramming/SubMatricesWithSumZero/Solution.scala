package interviewbit.Programming.DynamicProgramming.SubMatricesWithSumZero

import scala.collection.mutable

// Difficulty: hard. I had to use all the hints to implement it. What I had tried on paper was going in the right direction
//             but I never got to connect the dots to solve the problem. There are several ideas to have a connect together
//             to solve this problem, which makes it a hard one in my opinion.

// https://www.interviewbit.com/problems/sub-matrices-with-sum-zero/
object Solution {
  def main(args: Array[String]): Unit = {
    println(solve(Array())) // 0
    println(solve(Array(
      Array(-8, 5, 7),
      Array(3, 7, -8),
      Array(5, -8, 9)
    ))) // 2
    println(solve(Array(
      Array(0, 0),
      Array(0, 0)
    ))) // 9
  }

  def solve(matrix: Array[Array[Int]]): Int = {
    if (matrix.length == 0) return 0

    val (n, m) = (matrix.length, matrix(0).length)
    val prefixSums = Array.ofDim[Int](n, m)
    for (i <- 0 until n; j <- 0 until m) {
      prefixSums(i)(j) = matrix(i)(j)
      if (i > 0         ) prefixSums(i)(j) += prefixSums(i - 1)(j    )
      if (j > 0         ) prefixSums(i)(j) += prefixSums(i    )(j - 1)
      if (i > 0 && j > 0) prefixSums(i)(j) -= prefixSums(i - 1)(j - 1)
    }

    var zeroSumSubMatrices = 0
    for (i1 <- 0 until n; i2 <- i1 until n; counter = new Counter(); j <- 0 until m) {
      // sum of the entire sub-matrix [i1, i2] x [0, j]
      val prefixSum = prefixSums(i2)(j) - (if (i1 == 0) 0 else prefixSums(i1 - 1)(j))
      zeroSumSubMatrices += (if (prefixSum == 0) 1 else 0) + counter(prefixSum)
      counter.increment(prefixSum)
    }

    zeroSumSubMatrices
  }

  private class Counter {
    private val occurrences = mutable.HashMap[Int, Int]()

    def apply(n: Int) = occurrences.getOrElse(n, 0)
    def increment(n: Int): Unit = occurrences += n -> (this(n) + 1)
  }
}
