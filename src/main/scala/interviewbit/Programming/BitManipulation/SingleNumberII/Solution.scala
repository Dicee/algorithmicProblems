package interviewbit.Programming.BitManipulation.SingleNumberII

// Difficulty: medium. The solution is really easy but I had to use heavy prompting to think about this. Obviously, there are trivial ways to solve this in less efficient ways,
//             I'm only talking about O(1) memory solutions. The solution is pretty elegant, I like it.

// https://www.interviewbit.com/problems/single-number-ii/
object Solution {
  def main(args: Array[String]): Unit = {
    println(singleNumber(Array(1, 1, 1, 2, 2, 2, 3)))
    println(singleNumber(Array(1, 1, 1, 3))) // 3
    println(singleNumber(Array(1, 1, 9, 1, 10, 5, 5, 10, 5, 8, 10, 8, 8))) // 9
  }

  def singleNumber(arr: Array[Int])  = {
    var (ones, twos) = (0x0, 0x0)
    for (n <- arr; i <- 0 until Integer.SIZE) {
      val mask = 1 << i
      if ((n & mask) > 0) {
        val occurredOnce  = (ones & mask) > 0
        val occurredTwice = (twos & mask) > 0

        if (occurredOnce || occurredTwice ) twos ^= mask
        if (occurredOnce || !occurredTwice) ones ^= mask
      }
    }
    ones
  }
}
