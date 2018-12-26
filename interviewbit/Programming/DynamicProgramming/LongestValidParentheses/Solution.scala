package interviewbit.Programming.DynamicProgramming.LongestValidParentheses

import scala.collection.mutable

// Difficulty: medium. I didn't find the DP formulation for this problem. My solution is probably slightly slower than
//             the DP approach and uses a bit more memory (both O(n) time and memory but mine uses more complex data
//             structures). In fact, I have seen purely stack-based solutions afterwards that were simpler than mine (only
//             stacking the indices of opening brackets) that I find better than the DP approach because more intuitive
//             and in many cases less memory-consuming.

// https://www.interviewbit.com/problems/longest-valid-parentheses/
object Solution {
  def main(args: Array[String]): Unit = {
    println(longestValidParentheses(")()())")) // 4
    println(longestValidParentheses(")(())")) // 4
    println(longestValidParentheses("()")) // 2
    println(longestValidParentheses(")))((((")) // 0
    println(longestValidParentheses("((())(")) // 4
    println(longestValidParentheses("()())))))((())()(()")) // 6
    println(longestValidParentheses("()())))))((())()(())")) // 10
    println(longestValidParentheses("(()())))))((())()(())))")) // 12
    println(longestValidParentheses("()())))))((())()(()))")) // 12
    println(longestValidParentheses("((()())))(()((()(((")) // 8
    println(longestValidParentheses(")()))(())((())))))())()(((((())())((()())(())((((())))())((()()))(()(((()()(()((()()))(())()))(((")) // 30
  }

  def longestValidParentheses(s: String) = {
    val indexForSameDepth = mutable.HashMap[Int, mutable.ArrayStack[Int]]() += 0 -> mutable.ArrayStack(-1)
    var (depth, longest) = (0, 0)

    for (i <- s.indices) {
      depth = Math.max(0, depth) + (if (s(i) == '(') 1 else -1)
      if (depth < 0) { indexForSameDepth.clear(); indexForSameDepth += 0 -> mutable.ArrayStack(i) }
      else {
        indexForSameDepth.get(depth) match {
          case Some(mutable.ArrayStack()) | None => indexForSameDepth += depth -> mutable.ArrayStack(i)
          case Some(indices) =>
            if (s(i) == ')') {
              indexForSameDepth(depth + 1).pop()
              longest = Math.max(longest, i - indices.head)
            } else indices.push(i)
        }
      }
    }
    longest
  }
}
