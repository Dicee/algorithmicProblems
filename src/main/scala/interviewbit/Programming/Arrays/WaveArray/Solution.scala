package interviewbit.Programming.Arrays.WaveArray

import scala.util.Sorting

// Difficulty: trivial

// https://www.interviewbit.com/problems/wave-array/
object Solution {
  def main(args: Array[String]): Unit = {
    println(wave(Array(3, 2, 4, 1)).toList) // List(2, 1, 4, 3)
    println(wave(Array(5, 1, 3, 2, 4)).toList) // List(2, 1, 4, 3, 5)
  }

  def wave(arr: Array[Int])  = {
    Sorting.quickSort(arr)

    arr.indices.grouped(2).foreach {
      case Seq(i, j) =>
        val tmp = arr(i)
        arr(i) = arr(j)
        arr(j) = tmp

      case Seq(_) => // last group with a single element, do nothing
    }

    arr
  }
}
