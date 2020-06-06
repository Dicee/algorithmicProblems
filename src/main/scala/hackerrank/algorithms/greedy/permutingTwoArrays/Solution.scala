package hackerrank.algorithms.greedy.permutingTwoArrays

// Difficulty: very easy

// https://www.hackerrank.com/challenges/two-arrays/problem
object Solution {
  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.stdin.getLines()
    for (_ <- 1 to lines.next().toInt) {
      val Array(_, k)  = lines.next().split(' ').map(_.toInt)
      val ordering     = implicitly[Ordering[Int]]
      val (arr1, arr2) = (sortArray(lines.next(), ordering), sortArray(lines.next(), ordering.reverse))
      println(if (arr1.zip(arr2).forall { case (a, b) => a + b >= k}) "YES" else "NO")
    }
  }

  private def sortArray(s: String, ordering: Ordering[Int]) = s.split(' ').view.map(_.toInt).sorted(ordering).toArray
}
