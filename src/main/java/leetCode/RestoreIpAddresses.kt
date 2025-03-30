/**
 * Difficulty: easy, marked as medium. I did waste some time trying to make a backtracking solution work, which involved pretty tricky
 *             state management, before I pause and think "Dude, solutions are at most 12 characters long, this can just be brute-forced!".
 *             After that, it's pretty easy.
 *
 * https://leetcode.com/problems/restore-ip-addresses
 */
fun restoreIpAddresses(s: String): List<String> {
    if (s.length < 4 || s.length > 12) return emptyList()

    val solutions = mutableListOf<String>()

    // ok to brute-force because it's a small space to explore and it makes the solution very simple
    for (i in 1..3) {
        if (!isValidPart(0, i, s)) continue
        for (j in i + 1..i+ 3) {
            if (!isValidPart(i, j, s)) continue
            for (k in j + 1..j + 3) {
                if (isValidPart(j, k, s) && isValidPart(k, s.length, s)) {
                    solutions.add(buildString {
                        append(s, 0, i)
                        append('.')
                        append(s, i, j)
                        append('.')
                        append(s, j, k)
                        append('.')
                        append(s, k, s.length)
                    })
                }
            }
        }
    }

    return solutions
}

// could use toInt as well but it felt like cheating (:D) and requires string allocations
private fun isValidPart(i: Int, j: Int, s: String): Boolean =
    i < s.length &&
    j <= s.length &&
    when (j - i) {
        1 -> true
        2 -> s[i] != '0'
        3 -> when {
            s[i] == '0' -> false
            s[i] == '2' -> s[i + 1] == '5' && s[i + 2] <= '5' || s[i + 1] < '5'
            s[i] > '2' -> false
            else -> true
        }
        else -> false
    }

restoreIpAddresses("25525511135") // [255.255.11.135, 255.255.111.35]
restoreIpAddresses("0000") // [0.0.0.0]
restoreIpAddresses("101023") // ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
restoreIpAddresses("25525511135") // ["255.255.11.135","255.255.111.35"]
restoreIpAddresses("28576") // ["2.8.5.76","2.8.57.6","2.85.7.6","28.5.7.6"]
