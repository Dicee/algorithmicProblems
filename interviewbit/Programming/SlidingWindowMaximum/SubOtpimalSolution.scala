package interviewbit.Programming.SlidingWindowMaximum

import scala.collection.mutable

// Difficulty: medium. I wasn't too far from finding the optimal solution (Solution.scala, same package)
//             but I abandoned my idea and used a heap instead. It's only when I checked the hints that I
//             confirmed my feeling that it had to be doable in linear time and implemented it using the hint.
//             The solution in this file is O(n.log(n)) in time and O(n) in space but still scored 100%.

// https://www.interviewbit.com/problems/sliding-window-maximum/
object SubOtpimalSolution {
  def main(args: Array[String]): Unit = {
    println(slidingMaximum(Array(1, 3, -1, -3, 5, 3, 6, 7), 3).toList) // List(3, 3, 5, 5, 6, 7)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 5).toList) // List(5, 5, 5, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 4).toList) // List(4, 5, 5, 5, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 3).toList) // List(4, 3, 5, 5, 5, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 2).toList) // List(4, 3, 3, 5, 5, 2, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 1).toList) // List(4, 2, 3, -3, 5, 1, 2, 6)
  }

  def slidingMaximum(arr: Array[Int], window: Int) = {
    if (window >= arr.length) Array(arr.max)
    else {
      val maxByWindow = Array.ofDim[Int](arr.length - window + 1)
      val counter     = new Counter
      val heap        = mutable.PriorityQueue[Int]()

      for (i <- 0 until window) {
        counter.increment(arr(i))
        heap += arr(i)
      }
      maxByWindow(0)

      for (i <- maxByWindow.indices; j = i + window) {
        while (counter(heap.head) == 0) heap.dequeue()

        val previousMax = heap.head
        maxByWindow(i) = previousMax

        counter.decrement(arr(i))
        if (j < arr.length) {
          counter.increment(arr(j))
          heap += arr(j)
        }
      }

      maxByWindow
    }
  }

  private class Counter {
    private val occurrences = mutable.HashMap[Int, Int]()

    def apply(n: Int) = occurrences.getOrElse(n, 0)
    def increment(n: Int) = add(n,  1)
    def decrement(n: Int) = add(n, -1)
    private def add(n: Int, k: Int) = { val count = this(n) + k; occurrences += n -> count; count }
  }
}
