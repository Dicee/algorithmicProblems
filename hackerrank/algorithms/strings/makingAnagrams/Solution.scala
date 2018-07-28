import hackerrank.algorithms.strings.makingAnagrams

// Difficulty: easy

// https://www.hackerrank.com/challenges/making-anagrams/problem
object Solution {
    def makingAnagrams(s1: String, s2: String): Int = {
        val chars = s1.view.map((_, 1)) ++ s2.view.map((_, -1))
        chars.groupBy(_._1)
             .view
             .map(entry => entry._2.view.map(_._2))
             .map(occurrences => Math.abs(occurrences.sum))
             .sum
    }
}
