import scala.io.Source

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/utopian-tree
object Solution {
    def main(args: Array[String]) {        
        lazy val heightSeriesWithIndex = Stream.iterate((1, 0)) { 
            case (current, i) => (if (i % 2 == 0) current * 2 else current + 1, i + 1)
        }
        println(Source.stdin.getLines.toList.tail.map(line => heightSeriesWithIndex(line.toInt)._1).mkString("\n"))      
    }
}