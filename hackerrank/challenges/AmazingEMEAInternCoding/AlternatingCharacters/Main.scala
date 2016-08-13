import scala.io.Source

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/alternating-characters
object Solution {
    def main(args: Array[String]) {
        println(Source.stdin.getLines.toList.tail
                      .map(_.toList)
                      .map(chars => chars.tail.zip(chars)
                      .count(t => t._1 == t._2))
                      .mkString("\n"))      
    }
}