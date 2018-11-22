package interviewbit.Programming.Checkpoint.Level5

// Difficulty: easy

// https://www.interviewbit.com/problems/longest-consecutive-sequence/
import scala.collection.mutable
import scala.math._

object LongestConsecutiveSequence {
  def main(args: Array[String]): Unit = {
    println(longestConsecutive(Array(100, 4, 200, 1, 3, 2))) // 4
    println(longestConsecutive(Array(700, 699, 4, 697, 3, 5))) // 3
    println(longestConsecutive(Array(700, 699, 4, 697, 3, 698, 5))) // 4
  }

  def longestConsecutive(arr: Array[Int]) = {
    val set = mutable.HashSet[Int]() ++= arr

    def consumeConsecutiveElements(exclusiveStart: Int, step: Int) =
      Stream.from(exclusiveStart + step, step).takeWhile(set.remove).length

    var longestSequence = 0
    for (value <- arr) {
      var count = 0
      if (set.remove(value)) {
        count += 1
        count += consumeConsecutiveElements(value, -1)
        count += consumeConsecutiveElements(value,  1)
      }
      longestSequence = max(longestSequence, count)
    }

    longestSequence
  }
}
