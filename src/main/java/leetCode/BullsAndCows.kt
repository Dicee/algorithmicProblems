import kotlin.math.min

/**
 * Difficulty: trivial, marked as medium. No difficulty at all.
 *
 * https://leetcode.com/problems/bulls-and-cows/
 */
fun getHint(secret: String, guess: String): String {
    var correctCount = 0
    val unmatchedDigits = IntArray(10) { 0 }
    val incorrectlyGuessedDigits = IntArray(10) { 0 }

    secret.asSequence().zip(guess.asSequence()).forEach { (s, g) ->
        if (s == g) correctCount++
        else {
            unmatchedDigits[s.digitToInt()]++
            incorrectlyGuessedDigits[g.digitToInt()]++
        }
    }

    val partialMatches = unmatchedDigits.asSequence().zip(incorrectlyGuessedDigits.asSequence())
        .map { (s, g) -> min(s, g) }
        .sum()

    return "${correctCount}A${partialMatches}B"
}

getHint("1807", "7810") // 1A3B
getHint("1123", "0111") // 1A1B
