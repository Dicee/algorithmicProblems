package codility.lessons.sorting

import scala.util.Random

/**
 * Painless
 */
object MaxProductOfThreeScala extends App {
    object Solution {
        def solution(arr: Array[Int]) = { 
            val sorted = arr.sorted
            val n      = sorted.length 
            Math.max(sorted(0    ) * sorted(1    ) * sorted(n - 1), 
                     sorted(n - 3) * sorted(n - 2) * sorted(n - 1))
         }
    }
}