package hackerrank.algorithms.dynamicProgramming.theLongestCommonSubsequence

/**
 * Difficulty: medium. It's a big classic, but not that straightforward to come up with the idea without knowing it.
 */
// https://www.hackerrank.com/challenges/dynamic-programming-classics-the-longest-common-subsequence/problem
object Solution {
    def main(args: Array[String]) {
        val lines = scala.io.Source.stdin.getLines()
        lines.next()
        
        val (s1, s2) = (parseLine(lines.next()), parseLine(lines.next()))
        val matrix   = Array.ofDim[Int](s1.length + 1, s2.length + 1)
        
        var (maxi, maxj) = (0, 0)
        for (i <- 1 until matrix.length) {
            for (j <- 1 until matrix(0).length) {
                if (s1(i - 1) == s2(j - 1)) {
                    matrix(i)(j) = 1 + matrix(i - 1)(j - 1)
                    if (matrix(i)(j) > matrix(maxi)(maxj)) {
                        maxi = i
                        maxj = j
                    }
                } else matrix(i)(j) = Math.max(matrix(i)(j - 1), matrix(i - 1)(j))
            }
        }
        
        var (i, j) = (maxi, maxj)
        val buffer = new scala.collection.mutable.ArrayBuffer[Int]
        var len    = matrix(maxi)(maxj)
        
        while (len > 0) {      
            if (s1(i - 1) == s2(j - 1)) { 
                buffer += s1(i - 1)
                i      -= 1
                j      -= 1 
                len    -= 1                
            }
            else if (matrix(i)(j - 1) == len) j -= 1
            else i -= 1
        }
        print(buffer.reverse.mkString(" "))
    }
    
    private def parseLine(line: String) = line.split(' ').map(_.toInt)
}
