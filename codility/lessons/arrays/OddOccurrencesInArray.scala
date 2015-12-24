package codility.lessons.arrays;

object SolutionScala extends App {
    def solution(arr: Array[Int]): Int = {
        val numbers = scala.collection.mutable.HashMap[Int,Int]()
        for (x <- arr) numbers += x -> ((numbers.getOrElse(x,0) + 1) % 2)
        numbers.filter { case (_,v) => v == 1 }.keySet.last
    }
}
