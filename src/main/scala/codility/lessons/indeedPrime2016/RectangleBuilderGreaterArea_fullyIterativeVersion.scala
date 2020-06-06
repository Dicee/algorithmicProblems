package codility.lessons.indeedPrime2016

import scala.collection.mutable.ListBuffer

/**
 * Level: respectable
 * Note: more efficient than the partially functional version, at the cost of complexity
 */
object RectangleBuilderGreaterArea_fullyIterativeVersion extends App {
  def solution(arr: Array[Int], requiredArea: Int): Int = {
    if (arr.isEmpty) return 0

    var validSquares = 0

    def sortAndCollapse(arr: Array[Int]): Array[Long] = {
      val sortedArr = arr.sorted.map(_.toLong)
      val buffer    = ListBuffer[Long]()

      var count   = 1
      var current = sortedArr(0)
      for (i <- 1 until sortedArr.length) {
        if (current == sortedArr(i)) count += 1
        if (current != sortedArr(i) || i == sortedArr.length - 1) {
          if (count >= 2) {
            buffer += current
            if (count >= 4 && current * current >= requiredArea) validSquares += 1
          }
          count = 1
          current = sortedArr(i)
        }
      }
      buffer.toArray
    }

    val sortedPens   = sortAndCollapse(arr)
    var combinations = 0
    var (i, j)       = (0, sortedPens.length - 1)

    while (i < j) {
      if (sortedPens(i) * sortedPens(j) >= requiredArea) {
        // since the pens are sorted we know all pens k between i and j - 1 verify sortedPens(k) * sortedPens(j) >= requiredArea
        combinations += j - i
        j -= 1

        if (combinations > 1000000000) return -1
      } else {
        i += 1
      }
    }

    combinations + validSquares
  }
}
