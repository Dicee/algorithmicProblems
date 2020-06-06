package hackerrank.interviewPreparationKit.miscellaneous.flippingBits

// Difficulty: trivial

// https://www.hackerrank.com/challenges/flipping-bits/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=miscellaneous
object Solution {
    def flippingBits(n: Long): Long = {
        var (flipped, remain) = (0L, n)
        for (i <- 0 to 31) {
            val flippedBit = (remain + 1) % 2
            flipped += flippedBit << i
            remain >>= 1
        }
        flipped
    }
}
