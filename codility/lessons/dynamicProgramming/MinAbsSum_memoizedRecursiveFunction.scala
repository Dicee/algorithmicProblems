package codility.lessons.dynamicProgramming

/**
 * Level: ambitious
 *
 * Note: this solution does not meet all the performance requirements. For large problems, it either fails to terminate
 *       before the timeout or crashes due to a stack overflow
 */
object MinAbsSum_memoizedRecursiveFunction {
  def solution(arr: Array[Int]): Int = {
    val positiveArr = arr.map(Math.abs)

    val cache = scala.collection.mutable.Map[(Int, Int), Int]()
    def recSol(i: Int, currentCost: Int = 0): Int = {
      cache.getOrElseUpdate((i, currentCost),
        if (i >= positiveArr.length) currentCost
        else {
          val rec1 = recSol(i + 1, currentCost + positiveArr(i))
          val rec2 = recSol(i + 1, currentCost - positiveArr(i))
          Math.min(Math.abs(rec1), Math.abs(rec2))
        }
      )
    }
    recSol(0)
  }
}
