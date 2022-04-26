package leetCode

fun main() {
    assert(!isMatch("aa", "a"))
    assert(isMatch("aa", "a*"))
    assert(isMatch("ab", ".*"))
    assert(isMatch("a", "ab*"))
    assert(isMatch("", ".*"))
    assert(isMatch("", "a*"))
    assert(!isMatch("", "aa*"))
    assert(!isMatch("", "ab"))
    assert(isMatch("abcdefg", "ab.defg"))
    assert(isMatch("abccccdefg", "abc*defg"))
    assert(isMatch("abccccdefg", "abc*cdefg"))
    assert(isMatch("abdefg", "abc*defg"))
    assert(!isMatch("abcdefgz", "abc*defg"))
    assert(!isMatch("abcdefg", "abc*defgz"))
    assert(isMatch("abcdefg", "ab.*g"))
    assert(isMatch("abcdefg", "ab.*"))
    assert(isMatch("abcdefg", "ab.*.."))
    assert(isMatch("abcdefg", "ab.*....."))
    assert(!isMatch("abcdefg", "ab.*......"))
    assert(isMatch("aabcbcbcaccbcaabc", ".*a*aa*.*b*.c*.*a*"))
}

private fun isMatch(s: String, regex: String): Boolean {
    fun charMatches(i: Int, ri: Int) = s[i] == regex[ri] || regex[ri] == '.'
    fun recSol(i: Int, ri: Int): Boolean {
        if (ri >= regex.length) return i >= s.length

        if (ri < regex.lastIndex && regex[ri + 1] == '*') {
            if (recSol(i, ri + 2)) return true
            if (i < s.length && charMatches(i, ri) && recSol(i + 1, ri)) return true
            return false
        }
        // can just do a mindless comparison because s only contains letters, no special character
        if (i < s.length && charMatches(i, ri) && recSol(i + 1, ri + 1)) return true

        return false
    }

    return recSol(0, 0)
}