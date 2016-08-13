package hackerrank.challenges.AmazingEMEAInternCoding.GreedyFlorist

import scala.io.Source
    
// https://www.hackerrank.com/contests/amazing-intern-coding-challenge-2/challenges/greedy-florist
object Solution {
    def main(args: Array[String]) {        
        val lines      = Source.stdin.getLines  
        val nCustomers = lines.next().split(" ")(1).toInt
        val prices     = lines.next().split(" ").map(_.toInt).sorted(Ordering[Int].reverse)        
        println(prices.grouped(nCustomers).zipWithIndex.map { case (highestPrices, nthPurchase) => highestPrices.map((nthPurchase + 1) * _).sum }.sum)
    }
}