package hackerrank.challenges.AmazingEMEAInternCoding.MarkAndToys

import scala.collection.mutable.PriorityQueue

// Difficulty: medium. There's a trivial naive solution, which can be refined in a more subtle solution.

/**
 * Implementation note:
 *
 * Of course, a trivial way to solve the problem is to sort the list of prices and to iterate from the lowest
 * element to the highest until the sum of the explored elements exceeds the total budget.
 *
 * However, although the worst case complexity of my solution is the same as for such naive solution, it will
 * perform much better when the budget only allows to buy a small subset of items or simply when there are a
 * huge number of items.
 *
 * The main win will be memory-wise since my solution avoids storing all the elements and just streams over them.
 * In extreme cases where there are a lot of very expensive items (relative to the budget), my solution will also
 * avoid a lot of comparisons since it won't even try to insert them in the queue.
 */

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/mark-and-toys▬
object Solution {
  def main(args: Array[String]) {
    val sc               = new java.util.Scanner(System.in)
    var Array(n, budget) = sc.nextLine().split(" ").map(_.toInt)
    val cheapestToys     = new PriorityQueue[Int]
    for (i <- 1 to n) {
      val price = sc.nextInt()
      // cheapestToys.head is the most expensive of the cheapest toys
      if (cheapestToys.nonEmpty && budget <= price && price <= cheapestToys.head) budget += cheapestToys.dequeue()
      if (price <= budget) {
        budget -= price
        cheapestToys.enqueue(price)
      }
    }
    println(cheapestToys.size)
  }
}
