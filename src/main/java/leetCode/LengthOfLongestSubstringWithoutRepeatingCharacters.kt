package leetCode

import kotlin.math.max

// Difficulty: easy. Looks like a lot of other sliding window-like problems.

// https://leetcode.com/problems/longest-substring-without-repeating-characters
fun main() {
    println(lengthOfLongestSubstring("abcabcbb")) // 3
    println(lengthOfLongestSubstring("pwwkew")) // 3
    println(lengthOfLongestSubstring("")) // 0
}

private fun lengthOfLongestSubstring(s: String): Int {
    if (s.isEmpty()) return 0

    val chars = mutableSetOf(s[0])
    var (i, j) = 0 to 1
    var result = 1

    while (j <= s.lastIndex) {
        if (!chars.add(s[j])) {
            do { chars.remove(s[i++]) } while (s[i - 1] != s[j])
            chars.add(s[j])
        }
        result = max(result, chars.size)
        j++
    }

    return result
}