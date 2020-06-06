package interviewbit.Programming.SlidingWindowMaximum

// Difficulty: medium. I wasn't too far from finding the optimal solution (this one) but I abandoned my idea
//             and used a heap instead. It's only when I checked the hints that I confirmed my feeling that
//             it had to be doable in linear time and implemented it using the hint. The solution in this file
//             is linear in time and space.

// https://www.interviewbit.com/problems/sliding-window-maximum/
object Solution {
  def main(args: Array[String]): Unit = {
    println(slidingMaximum(Array(1, 3, -1, -3, 5, 3, 6, 7), 3).toList) // List(3, 3, 5, 5, 6, 7)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 5).toList) // List(5, 5, 5, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 4).toList) // List(4, 5, 5, 5, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 3).toList) // List(4, 3, 5, 5, 5, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 2).toList) // List(4, 3, 3, 5, 5, 2, 6)
    println(slidingMaximum(Array(4, 2, 3, -3, 5, 1, 2, 6), 1).toList) // List(4, 2, 3, -3, 5, 1, 2, 6)
  }

  def slidingMaximum(arr: Array[Int], window: Int) = {
    type JDeque[T] = java.util.ArrayDeque[T]

    def insert(n: Int, i: Int, deque: JDeque[(Int, Int)]): Unit = {
      def clearIrrelevant(peek: () => (Int, Int), poll: () => Unit) = while (!deque.isEmpty && (peek()._1 <= n || peek()._2 < i - window + 1)) poll()

      clearIrrelevant(deque.peekFirst, deque.pop)
      clearIrrelevant(deque.peekLast, deque.pollLast)
      deque.addLast((n, i))
    }

    val deque = new JDeque[(Int, Int)]
    for (i <- 0 until window) insert(arr(i), i, deque)

    val maxByWindow = Array.ofDim[Int](arr.length - window + 1)
    for (i <- maxByWindow.indices; j = i + window) {
      maxByWindow(i) = deque.peekFirst()._1
      if (j < arr.length) insert(arr(j), j, deque)
    }

    maxByWindow
  }
}
