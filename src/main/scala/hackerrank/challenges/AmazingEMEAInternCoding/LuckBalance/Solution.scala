package hackerrank.challenges.AmazingEMEAInternCoding.LuckBalance

import scala.io.Source

// Difficulty: pretty straightforward. No trap, no scaling problem.

// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/luck-balance
object Solution {
    def main(args: Array[String]) {
        val lines = Source.stdin.getLines
        val k     = lines.next().split(" ")(1).toInt
        val contests = lines.toList.map(_.split(" ") match { case Array(luck, importantFlag) => (luck.toInt, importantFlag == "1") })
            
        val unimportantContestsLuckSum   = contests.filterNot(_._2).map(_._1).sum
            
        val sortedImportantContestsLuck  = contests.filter(_._2).map(_._1).sorted(Ordering[Int].reverse)
        val lostImportantContestsLuckSum = sortedImportantContestsLuck.iterator.take(k).sum
        val wonImportantContestsLuckSum  = sortedImportantContestsLuck.iterator.drop(k).sum

        println(unimportantContestsLuckSum + lostImportantContestsLuckSum - wonImportantContestsLuckSum)
    }
}