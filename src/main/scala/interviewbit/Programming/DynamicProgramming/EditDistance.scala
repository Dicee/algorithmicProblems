package interviewbit.Programming.DynamicProgramming

import scala.math._

// Difficulty: easy to see it's DP, and easy to find the formula.

// https://www.interviewbit.com/problems/edit-distance/
class Solution {  
    //  if j == -1      S(i, j) = i + 1                // deletions
    //  if i == -1      S(i, j) = j + 1                // insertions
    //  if A(i) == B(j) S(i, j) = S(i - 1, j - 1)
    //  else            S(i, j) = 1 + min(
    //                                S(i - 1, j - 1), // replace
    //                                S(i - 1, j    ), // delete
    //                                S(i    , j - 1)  // insert
    //                            )
    def minDistance(input: String, target: String): Int = {
        val dp = Array.ofDim[Int](input.length + 1, target.length + 1)
    
        for (i <- -1 until input.length; j <- -1 until target.length) {
          dp(i + 1)(j + 1) =
            if      (i == -1) j + 1
            else if (j == -1) i + 1
            else if (input(i) == target(j)) dp(i)(j)
            else 1 + min(min(dp(i)(j), dp(i)(j + 1)), dp(i + 1)(j))
        }
    
        dp(input.length)(target.length)
    }
}
