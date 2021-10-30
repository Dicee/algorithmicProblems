package hackerrank.algorithms.implementation.threeDSurfaceArea

import scala.math._

// Difficulty: easy

// https://www.hackerrank.com/challenges/3d-surface-area/problem
class Solution {
  def surfaceArea(arr: Array[Array[Int]]): Int = {
    val (h, w) = (arr.length, arr(0).length)  
    val directions = Seq((0, 1), (-1, 0), (0, -1), (1, 0))

    var area = 0

    def calculateNonSharedArea(curr: (Int, Int), neigh: (Int, Int)) = {
      val height = arr(curr._1)(curr._2)
      if (neigh._1 < 0 || neigh._1 >= h || neigh._2 < 0 || neigh._2 >= w) height
      else max(height - arr(neigh._1)(neigh._2), 0)
    }

    for (i <- arr.indices; j <- arr(0).indices) {
      area += 2 + directions // top and bottom faces are always exposed, for others we need to check all 4 neighbours
           .map { case (di, dj) => calculateNonSharedArea((i, j), (i + di, j +dj)) }
           .sum
    }

    area
  }
}
