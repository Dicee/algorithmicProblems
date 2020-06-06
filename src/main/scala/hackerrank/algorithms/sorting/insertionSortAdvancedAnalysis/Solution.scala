package hackerrank.algorithms.sorting.insertionSortAdvancedAnalysis

  // Difficulty: easy. Although HackerRank marks it as "Advanced", it is quick to see we need to calculate the number of
  //             inversions in the array, which is a classical problem.

  // https://www.hackerrank.com/challenges/insertion-sort
  object Solution {
    def main(args: Array[String]): Unit = {
      val lines  = scala.io.Source.stdin.getLines()
      val nTests = lines.next().trim.toInt
      for (_ <- 1 to nTests) {
        lines.next()
        val arr = lines.next().trim.split(' ').map(_.toInt)
        println(mergeSortCountingInversions(arr))
      }
    }

    private def mergeSortCountingInversions(arr: Array[Int]): Long = mergeSortCountingInversions(arr, 0, arr.length)
    private def mergeSortCountingInversions(arr: Array[Int], start: Int, end: Int): Long = {
      if (start >= end - 1) return 0L

      def mergeCountingInversions(left: Array[Int], right: Array[Int]) = {
        var (leftIndex, rightIndex, inversionsCount) = (0, 0, 0L)
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
