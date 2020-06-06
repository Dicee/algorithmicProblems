package interviewbit.Programming.DynamicProgramming

import scala.math._

// Difficulty: hard. Didn't find the optimal greedy solution on my own. DP is trivial but doesn't
//             pass all tests.

// https://www.interviewbit.com/problems/min-jumps-array/
object MinJumpsArray {
  def main(args: Array[String]): Unit = {
    println(jump(Array(2, 3, 1, 1, 4))) // 2
    println(jump(Array(1, 0, 100, 0, 0, 0, 1))) // -1
    println(jump(Array(2, 0, 100, 0, 0, 0, 1))) // 2
    println(jump(Array(1, 10, 100, 0, 0, 0, 1))) // 2
    println(jump(Array(1, 3, 100, 0, 0, 0, 1))) // 3
    println(jump(Array(1, 4, 3, 0, 0, 1, 1))) // 3
    println(jump(Array(1, 4, 3, 0, 1, 0, 1))) // -1
  }

  def jump(arr: Array[Int]): Int  = {
    var maxReachable = 0
    var maxReachableWithAdditionalJump = arr(0)
    var jumps = 0
    var pos = 0

    while (maxReachable < arr.length - 1 && pos <= maxReachable) {
      maxReachableWithAdditionalJump = max(maxReachableWithAdditionalJump, pos + arr(pos))

      if (pos == maxReachable) {
        maxReachable = maxReachableWithAdditionalJump
        jumps += 1
      }

      pos += 1
    }

    if (maxReachable < arr.length - 1) -1 else jumps
  }

//  // DP approach fails some test with time exceeded
//  def jump(arr: Array[Int]): Int  = {
//    val dp = Array.fill(arr.length)(-1)
//    dp(0) = 0
//
//    for ((maxJump, i) <- arr.view.zipWithIndex) {
//      if (dp(i) >= 0) {
//        for (jump <- 1 to maxJump; if i + jump < arr.length) {
//          if (dp(i + jump) < 0 || dp(i) + 1 < dp(i + jump))
//            dp(i + jump) = dp(i) + 1
//        }
//      }
//    }
//    dp.last
//  }
}
