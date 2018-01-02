package hackerrank.algorithms.constructiveAlgorithms.bonetrousle

// Difficulty: hard. As always, the solution is easy, but I needed a hint to find it. I achieved a partial score
//             (something like 70%) with a rule-based solution but it was doomed to fail as not all problems that
//             admitted at least one solution had a solution fitting my criteria. The idea was essentially to decompose
//             n such that n = (b - 1) * i + j. Starting from i, I was then adding (i + x) and (i - x), which sum is 2*i
//             until I reached (b - 1) * i. At this point, I tried using various rules to manage to add j to this sum.
//
//             What I liked about my solution is that I was not using any memory, representing the solution as a range
//             (optional central value, min value, max value) and a maximum of 3 other integers to add or remove from the
//             sum. However, it was naive to believe all solutions could fit this pattern and I tried too hard to make it
//             work before rethinking my approach.

// https://www.hackerrank.com/challenges/bonetrousle/problem
object Solution {
  def main(args: Array[String]) {
    scala.io.Source.stdin.getLines()
      .drop(1)
      .map(_.split(' '))
      .foreach { case Array(requiredSticks, totalBoxes, requiredBoxes) =>
        solve(requiredSticks.toLong, totalBoxes.toLong, requiredBoxes.toInt)}
  }

  private def solve(requiredSticks: Long, totalBoxes: Long, requiredBoxes: Int): Unit = {
    // initialize with the lowest possible sum
    val sol = (1 to requiredBoxes).map(_.toLong).toArray
    var sum = sol.sum

    if (sum > requiredSticks) { println(-1); return }

    var i = requiredBoxes - 1
    while (sum != requiredSticks && i >= 0) {
      // attempt to swap the value in sol(i) with a larger number to complete the sum or get closer. The last term
      // ensures we never pick the same number twice during the loop
      val valueToSwapWith = Math.min(requiredSticks - sum + sol(i), totalBoxes - (requiredBoxes - 1 - i))
      sum   += valueToSwapWith - sol(i)
      sol(i) = valueToSwapWith
      i     -= 1
    }
    println(if (sum != requiredSticks) -1 else sol.mkString(" "))
  }
}