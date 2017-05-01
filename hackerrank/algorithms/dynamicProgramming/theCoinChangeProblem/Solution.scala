package hackerrank.algorithms.dynamicProgramming.theCoinChangeProblem

// Difficulty: easy. Very classical.

// https://www.hackerrank.com/challenges/coin-change
object Solution {
  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.stdin.getLines()
    val goal  = lines.next().split(" ")(0).toInt
    val coins = lines.next().split(" ").map(_.toInt).sorted

    val solution = Array.ofDim[Long](goal + 1)
    solution(0)  = 1

    for (coin <- coins) {
        (0 to goal).takeWhile(coin + _ <= goal).foreach(i => solution(i + coin) += solution(i))
    }

    println(solution.last)
  }
}
