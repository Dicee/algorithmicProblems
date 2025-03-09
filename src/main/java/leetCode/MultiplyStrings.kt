/**
 * Difficulty: marked as medium but is easy. Without being able to execute though, I would have found it more difficult to not forget any edge case.
 *             It's a bit tedious to write, and of course super inefficient for BigInt multiplication, but I think it was the spirit of the question.
 * 
 * https://leetcode.com/problems/multiply-strings/description/
 */

fun multiply(num1: String, num2: String): String {
    if (num1 == "0" || num2 == "0") return "0"

    var n1: String = num1
    var n2: String = num2

    if (n1.length < n2.length) {
        val tmp = n1
        n1 = num2
        n2 = tmp
    }

    val intermediateResults = getIntermediateResults(n2, n1)
    return sum(intermediateResults)
}

fun getIntermediateResults(n2: String, n1: String): List<String> {
    val intermediateResults = mutableListOf<String>()

    for (i2 in n2.lastIndex downTo 0) {
        var carryOver = 0

        val intermediateResult = buildString {
            for (k in n2.lastIndex - i2 downTo 1) append('0')

            for (i1 in n1.lastIndex downTo 0) {
                val m = n1[i1].digitToInt() * n2[i2].digitToInt() + carryOver
                val (q, r) = Pair(m / 10, m % 10)

                append(r.digitToChar())
                carryOver = q
            }

            if (carryOver > 0) append(carryOver.digitToChar())
        }
        intermediateResults.add(intermediateResult)
    }

    return intermediateResults
}

fun sum(intermediateResults: List<String>) = buildString {
    val maxLength = intermediateResults.maxOf { it.length }

    var carryOver = 0

    for (i in 0 until maxLength) {
        var sum = carryOver

        for (intermediateResult in intermediateResults) {
            if (i < intermediateResult.length) sum += intermediateResult[i].digitToInt()
        }

        val (q, r) = Pair(sum / 10, sum % 10)

        append(r.digitToChar())
        carryOver = q
    }

    if (carryOver > 0) append(carryOver.digitToChar())
}.reversed()

// executing in REPL
multiply("123", "456")
multiply("2", "3")
multiply("295", "3")
multiply("3", "295")
multiply("295", "3098")
multiply("3098", "295")
