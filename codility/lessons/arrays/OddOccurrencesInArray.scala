package codility.lessons.arrays;

object SolutionScala extends App {
    def solution(arr: Array[Int]) = arr.reduce(_ ^ _)
}
