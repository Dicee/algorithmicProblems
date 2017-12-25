package hackerrank.algorithms.recursion.thePowerSum

// Difficulty: easy but fun. If it hadn't been labeled as "recursion", I would probably have looked for a DP solution instead,
//             which would have been less appropriate (sub-problems do not overlap).

// Basic idea: if 1 = p0, p1, p2, ..., pj are all successive integers i such that i^n <= x and S(0..j)(n) is the number of ways to get n by
//             summing unique elements among [p0..pj], then we can express the solution as follows:
//             S(0..j)(x) = S(0..k)(x - j) + S(0..j-1)(x) where k is the first integer such that pk^n <= x - j  

// https://www.hackerrank.com/challenges/the-power-sum/problem
object Solution {
    def numberOfWays(x:Int, n:Int): Int = {
       val powers = Stream.from(1).map(Math.pow(_, n).toInt).takeWhile(_ <= x).toList.reverse
       
        def recSol(x: Int, powers: List[Int]): Int = (x, powers) match {
            case (_, Nil)              => 0
            case (_, h :: t) if x == h => 1 + recSol(x, t)
            case (_, h :: t)           => recSol(x - h, powers.drop(1).dropWhile(_ > x - h)) + 
                                          recSol(x, t)    
        }
        
        recSol(x, powers)
    }

    def main(args: Array[String]) {
       println(numberOfWays(readInt(),readInt()))
    }
}
