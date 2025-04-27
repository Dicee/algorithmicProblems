/**
 * Difficulty: trivial, marked as medium. It's a "just-do-it" problem.
 *
 * https://leetcode.com/problems/decode-string
 */
fun decodeString(s: String): String {
    return buildString {
        var i = 0

        while (i < s.length && !s[i].isDigit()) append(s[i++])

        if (i < s.length) {
            val repeatStartIndex = i
            val openingBracketIndex = s.indexOf('[', i)
            i = openingBracketIndex + 1

            var counter = 1
            while (counter != 0) {
                if (s[i] == '[') counter++
                else if (s[i] == ']') counter--
                i++
            }

            val repeats = s.substring(repeatStartIndex, openingBracketIndex).toInt()
            append(decodeString(s.substring(openingBracketIndex + 1, i - 1)).repeat(repeats))
        }

        if (i < s.length) append(decodeString(s.substring(i, s.length)))
    }
}

decodeString("3[a]2[bc]") // aaabcbc
decodeString("3[a2[c]]") // accaccacc
decodeString("2[abc]3[cd]ef") // abcabccdcdcdef
