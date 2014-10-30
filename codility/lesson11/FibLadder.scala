import scala.math.pow

/**
 * Level : respectable
 */
object Solution {
    def solution(A: Array[Int], B: Array[Int]): Array[Int] = {
        val m = pow(2,B.max).toInt
        lazy val fibo : Stream[Int] = 1 #:: fibo.zip(0 #:: fibo).map(t => (t._1 + t._2) % m).take(A.max)
        val store = fibo.toArray
        (for (i <- 0 to A.length - 1) yield (store(A(i)) % pow(2,B(i)).toInt)).toArray
    }
}
