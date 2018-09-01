package algorithmicProblems.hackerrank.algorithms.strings.sherlockAndTheValidString

// Difficulty: easy. I still managed to miss an edge case of my first try though =(

// https://www.hackerrank.com/challenges/sherlock-and-valid-string
object Solution {
    def isValid(s: String): String = {
        val occurrences      = s.groupBy(identity).mapValues(_.size)
        val distinctValues   = TreeSet[Int]() ++ occurrences.values
        val sameOccurrences  = distinctValues.size == 1
        val onlyOneException = distinctValues.toList match {
            case a :: b :: Nil => 
                (b - a == 1 && occurrences.values.count(_ == b) == 1) || 
                (    a == 1 && occurrences.values.count(_ == a) == 1)
                
            case _ => false
        }
        if (sameOccurrences || onlyOneException) "YES" else "NO"
    }
}
