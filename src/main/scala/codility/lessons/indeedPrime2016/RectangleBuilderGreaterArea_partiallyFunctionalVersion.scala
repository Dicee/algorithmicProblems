package codility.lessons.indeedPrime2016

/**
 * Level: respectable
 * Note: simpler than the fully iterative version but slower
 */
object RectangleBuilderGreaterArea_partiallyFunctionalVersion extends App {
  def solution(arr: Array[Int], requiredArea: Int): Int = {
    val usablePens   = arr.groupBy(_.toLong).mapValues(_.length).filter { case (k, v) => v >= 2 }
    val validSquares = usablePens.count { case (k, v) => v >= 4 && k * k >= requiredArea }
    val sortedPens   = usablePens.keySet.toArray.sorted

    var combinations = 0
    var (i, j) = (0, sortedPens.length - 1)
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

  println(solution(Array(1, 2, 5, 1, 1, 2, 3, 5, 1), 5))
}
