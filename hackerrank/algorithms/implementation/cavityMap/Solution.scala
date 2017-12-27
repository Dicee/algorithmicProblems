package hackerrank.algorithms.implementation.cavityMap

// Difficulty: trivial although lengthy

// https://www.hackerrank.com/challenges/cavity-map/problem
object Solution {
  def main(args: Array[String]) {
    val lines  = scala.io.Source.stdin.getLines()
    val n      = lines.next().toInt
    val matrix = Array.ofDim[Int](n, n)

    for (i <- 0 until n; (v, j) <- lines.next().split("").view.map(_.toInt).zipWithIndex)
      matrix(i)(j) = v

    for (i <- 0 until n; j <- 0 until n) {
      print(if (isCavity(i, j, matrix)) "X" else matrix(i)(j))
      if (j == n - 1) println
    }
  }

  private def isCavity(i: Int, j: Int, matrix: Array[Array[Int]]) = {
    (for {
      di <- -1 to 1; dj <- -1 to 1
      if di == 0 ^ dj == 0
      ii = i + di; jj = j + dj
      if 0 < ii && 0 < jj && ii < matrix.length && jj < matrix.length
    } yield matrix(ii)(jj)).forall(_ < matrix(i)(j))
  }
}
