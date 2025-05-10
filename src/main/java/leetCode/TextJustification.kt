/**
 * Difficulty: easy, marked as hard. Just need to write it.
 *
 * https://leetcode.com/problems/text-justification
 */
val sep = " "

fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
    val justifiedLines = mutableListOf<String>()
    var i = 0

    while (i < words.size) {
        var wordsLen = 0
        var j = 0
        while (i + j < words.size && wordsLen + words[i + j].length + j - 1 < maxWidth) {
            wordsLen += words[i + j].length
            j++
        }
        justifiedLines.add(justifyLine(words, i, j, wordsLen, maxWidth))
        i += j
    }

    return justifiedLines
}

private fun justifyLine(words: Array<String>, i: Int, j: Int, wordsLen: Int, maxWidth: Int): String {
    return buildString {
        if (j == 1) {
            append(words[i])
            append(sep.repeat(maxWidth - wordsLen))
        } else if (i + j >= words.size) {
            append(words.asSequence().drop(i).take(j).joinToString(sep))
            append(sep.repeat(maxWidth - wordsLen - j + 1))
        } else {
            val padding = (maxWidth - wordsLen) / (j - 1)
            val paddingStr = sep.repeat(padding)

            var rest = maxWidth - wordsLen - (j - 1) * padding

            for (k in i until i + j) {
                append(words[k])
                if (k != i + j - 1) {
                    append(paddingStr)
                    if (rest > 0) {
                        append(sep)
                        rest--
                    }
                }
            }
        }
    }
}

fullJustify(arrayOf("This", "is", "an", "example", "of", "text", "justification."), 16)
fullJustify(arrayOf("What", "must", "be", "acknowledgment", "shall", "be"), 16)
fullJustify(arrayOf("Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"), 20)

