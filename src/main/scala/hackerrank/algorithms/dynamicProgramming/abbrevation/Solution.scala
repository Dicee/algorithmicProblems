package hackerrank.algorithms.dynamicProgramming.abbreviation

// Difficulty: medium/hard. Took me a while to get a perfect score to be honest. I obsessed myself over the idea that it could be done with slight modifications of the DP solution of the longest 
//             common subsequence problem but either that's not true, or I wasn't doing it properly. Goes fine when taking the time to pose the equations beforehand rather than jumping on
//             an intuition on a whim, as is generally the case with DP.

// https://www.hackerrank.com/challenges/abbr/problem?h_l=playlist&slugs%5B%5D=interview&slugs%5B%5D=interview-preparation-kit&slugs%5B%5D=dynamic-programming
object Solution {
    //           { a(n) == b(m) && f(n-1, m-1)          if b(m).isUpper && a(n) == b(m)                  (1)
    //           { f(n, m-1) || f(n-1, m-1)             if b(m).isLower && a(n).toUpper == b(m).toUpper  (2)
    // f(n, m) = { f(n, m-1)                            if b(m).isLower && a(n).toUpper != b(m).toUpper  (3)                               
    //           { f(n, m-1) && b(m).isLower            if n = 0 && m > 0                                (4)     
    //           { false                                if m = 0 && n > 0                                (5)
    //           { true                                 if n = m = 0                                     (6)
    //
    // (1): if b ends with an upper case letter, we can't skip the letter and we can't lowercase it so we're looking for an exact
    //      match with the last letter in a
    // (2): if b ends with a lower case letter and it matches case-insensitively with a's last leter, we can either skip the letter 
    //      in b and hope to have another match later on or consume it (so consume a's last letter at the same time)
    // (3): if b ends with a lower case letter and no match is possible with a's last letter, the only thing we can do is skip this letter
    // (4): if a is the empty string and b is not, the problem only has a solution if b is entirely made of lower case letters because we 
    //      need to skip them all
    // (5): if b is the empty string and b is not, there's no way that b can contain a subsequence that matches the elements of a
    // (6): if both a and b are empty strings, b trivially matches a
    def abbreviation(toModify: String, toMatch: String): String = {
        val (n, m) = (toMatch.length + 1, toModify.length + 1)
        val matrix = Array.fill(n, m)(false)
        matrix(0)(0) = true
        toModify.view.takeWhile(_.isLower).zipWithIndex.foreach { case (_, i) => matrix(0)(i + 1) = true }
        
        for (i <- 1 until n; j <- 1 until m) {
            matrix(i)(j) = 
                if (toModify(j - 1).isUpper) toModify(j - 1) == toMatch(i - 1) && matrix(i - 1)(j - 1)
                else if (toModify(j - 1).toUpper == toMatch(i - 1).toUpper) matrix(i)(j - 1) || matrix(i - 1)(j - 1)
                else matrix(i)(j - 1)
        }
        
        if (matrix(n - 1)(m - 1)) "YES" else "NO"
    }
}
