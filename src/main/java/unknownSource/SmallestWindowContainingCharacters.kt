package unknownSource

const val IMPOSSIBLE = "impossible"

fun main() {
    testWithFixedWindow("ABCABC", "ABBC", 4, true)
    testWithFixedWindow("ABCDZ", "AZ", 3, false)
    testSmallestWindow("ABCABC", "ABBC", "BCAB")
    testSmallestWindow("ABCDZ", "AZ", "ABCDZ")
    testSmallestWindow("this is a test string", "tist", "t stri")
    testSmallestWindow("blood for blood", "oro", "od for")
}

private fun findWindowContainingAllCharacters(s: String, chars: String, windowSize: Int): Boolean {
    if (chars.length > s.length) return false

    val charOccurrences = chars.groupingBy { it }.eachCount()
    val remainingOccurrences = HashMap(charOccurrences)

    var distinctCharsToMatch = remainingOccurrences.size

    for (i in 0..s.lastIndex) {
        if (updateOccurrences(s[i], remainingOccurrences, add = false) == 0) distinctCharsToMatch--
        if (i >= windowSize && s[i - windowSize] in charOccurrences) {
            if (updateOccurrences(s[i - windowSize], remainingOccurrences, add = true) == 1) distinctCharsToMatch++
        }

        if (distinctCharsToMatch == 0) return true
    }

    return false
}

private fun findSmallestWindowContainingAllCharacters(s: String, chars: String): String {
    if (chars.length > s.length) return IMPOSSIBLE

    val charOccurrences = chars.groupingBy { it }.eachCount()
    val remainingOccurrences = HashMap(charOccurrences)

    var start = 0
    var (optimalStart, minLength) = 0 to Int.MAX_VALUE
    var distinctCharsToMatch = remainingOccurrences.size

    for (i in 0..s.lastIndex) {
        if (s[i] in charOccurrences && updateOccurrences(s[i], remainingOccurrences, add = false) == 0) distinctCharsToMatch--

        if (distinctCharsToMatch == 0) {
            while (true) {
                val head = s[start]
                val count = remainingOccurrences[head]
                if (count == 0) break

                if (count != null) updateOccurrences(head, remainingOccurrences, add = true)
                start++
            }

            val len = i - start + 1
            if (minLength > len) {
                minLength = len
                optimalStart = start
            }
        }
    }

    return if (distinctCharsToMatch == 0) s.substring(optimalStart, optimalStart + minLength) else IMPOSSIBLE
}

private fun updateOccurrences(ch: Char, charOccurrences: MutableMap<Char, Int>, add: Boolean) =
        charOccurrences.merge(ch, if (add) 1 else -1) { o, n -> o + n }!!

private fun testWithFixedWindow(s: String, chars: String, windowSize: Int, expected: Boolean) {
    val result = findWindowContainingAllCharacters(s, chars, windowSize)
    if (result != expected) {
        throw RuntimeException("findWindowContainingAllCharacters($s, $chars, $windowSize) should yield $expected but was: $result")
    }
}

private fun testSmallestWindow(s: String, chars: String, expected: String) {
    val result = findSmallestWindowContainingAllCharacters(s, chars)
    if (result != expected) {
        throw RuntimeException("findSmallestWindowContainingAllCharacters(\"$s\", \"$chars\") should yield \"$expected\" but was: \"$result\"")
    }
}
