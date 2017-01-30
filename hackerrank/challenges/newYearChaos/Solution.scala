package hackerrank.challenges.newYearChaos

// Difficulty: doable. I did get bitten thinking I could make this work with a simple linear solution but my attempt failed.
// Fallbacking to counting the number of inversions, it then makes it a classical problem.

// https://www.hackerrank.com/challenges/new-year-chaos
object Solution {
  def main(args: Array[String]) {
    val lines = scala.io.Source.stdin.getLines()
    val nTestCases = lines.next().toInt
    for (_ <- 1 to nTestCases) {
      lines.next()
      val queue   = lines.next().split(' ').map(_.toInt)
      minimumNumberOfBribes(queue) match {
        case Some(bribes) => println(bribes)
        case None         => println("Too chaotic")
      }
    }
  }

  private val MaxBribesPerPerson = 2
  private def minimumNumberOfBribes(queue: Array[Int]): Option[Int] = {
        for ((ticketNumber, pos) <- queue.zipWithIndex) {
          if (ticketNumber - 1 - MaxBribesPerPerson > pos) return None
        }
    Some(mergeSortCountingInversions(queue))
  }

  private def mergeSortCountingInversions(arr: Array[Int]): Int = mergeSortCountingInversions(arr, 0, arr.length)
  private def mergeSortCountingInversions(arr: Array[Int], start: Int, end: Int): Int = {
    if (start >= end - 1) return 0

    def mergeCountingInversions(left: Array[Int], right: Array[Int]) = {
      var (leftIndex, rightIndex, inversionsCount) = (0, 0, 0)
      for (i <- start until end) {
        val (pickLeft, inversionsSubCount) =
          if      (rightIndex >= right.length)                                      (true , 0)
          else if (leftIndex == left.length || right(rightIndex) < left(leftIndex)) (false, left.length - leftIndex)
          else                                                                      (true , 0)

        inversionsCount += inversionsSubCount
        arr(i) = if (pickLeft) {
          val res     = left(leftIndex)
          leftIndex  += 1
          res
        } else {
          val res     = right(rightIndex)
          rightIndex += 1
          res
        }
      }
      inversionsCount
    }

    val mid   = (start + end) / 2
    val left  = arr.slice(start, mid)
    val right = arr.slice(mid, end)

    mergeSortCountingInversions(left ) +
    mergeSortCountingInversions(right) +
    mergeCountingInversions    (left, right)
  }
}