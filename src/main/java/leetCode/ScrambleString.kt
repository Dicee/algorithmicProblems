/**
 * Difficulty: hard. I would say it's a fair grade because though the basic idea is simple, it took me a while to get it to work for all cases,
 *             and optimize. This final version moves away from maintaining IntRange instances that represent which section of each string we
 *             are comparing. To me, doing that had to be faster and more memory-efficient as I was memoizing only ranges rather than full strings,
 *             and not having to allocate many strings. However, it didn't seem to make a dent in performance, and it complicated my logic enough to
 *             confuse me several times. Thus, I ended up opting for keeping this simpler solution which performs the same, however surprising this is.
 *
 * https://leetcode.com/problems/scramble-string
 */
class ScrambleString {
    fun isScramble(s1: String, s2: String): Boolean {
        return isScrambleRec(s1, s2)
    }

    private fun isScrambleRec(
        s1: String,
        s2: String,
        memoizedResults: MutableMap<Pair<String, String>, Boolean> = mutableMapOf(),
    ): Boolean {
        val memoized = memoizedResults[s1 to s2]
        if (memoized != null) return memoized

        fun memoizeResult(b: Boolean): Boolean {
            memoizedResults[s1 to s2] = b
            return b
        }

        if (!sameCharacters(s1, s2)) return false
        if (s1.length == 1) return true

        for (cutOffPoint in 0 until s1.length - 1) {
            val l1 = s1.slice(0..cutOffPoint)
            val r1 = s1.slice((cutOffPoint + 1) until s1.length)

            val l2 = s2.slice(0..cutOffPoint)
            val r2 = s2.slice((cutOffPoint + 1) until s2.length)

            if (
                isScrambleRec(l1, l2, memoizedResults) &&
                isScrambleRec(r1, r2, memoizedResults)
            ) return memoizeResult(true)

            val sl2 = s2.slice( s2.lastIndex - cutOffPoint until s2.length)
            val sr2 = s2.slice( 0 until (s2.lastIndex - cutOffPoint))
            if (
                isScrambleRec(l1, sl2, memoizedResults) &&
                isScrambleRec(r1, sr2, memoizedResults)
            ) return memoizeResult(true)
        }

        return memoizeResult(false)
    }

    private fun sameCharacters(s1: String, s2: String): Boolean {
        val occurrences = mutableMapOf<Char, Int>()
        s1.forEach { occurrences.merge(it, 1, Int::plus) }
        s2.forEach { occurrences.merge(it, -1, Int::plus) }
        return occurrences.values.all { it == 0 }
    }
}

// running in REPL
println(ScrambleString().isScramble("great", "rgeat")) // true
println(ScrambleString().isScramble("1", "1")) // true
println(ScrambleString().isScramble("a", "b")) // false
println(ScrambleString().isScramble("ab", "ab")) // true
println(ScrambleString().isScramble("ba", "ba")) // true
println(ScrambleString().isScramble("great", "rgeaf")) // false
println(ScrambleString().isScramble("abcde", "caebd")) // false
println(ScrambleString().isScramble("abcdbdacbdac", "bdacabcdbdac")) // true
println(ScrambleString().isScramble("abcdefghij", "abcdefghij")) // true
println(ScrambleString().isScramble("ccabcbabcbabbbbcbb", "bbbbabccccbbbabcba")) // false

