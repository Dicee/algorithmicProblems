package hackerrank.algorithms.search.missingNumbers

import scala.collection.mutable

// Difficulty: trivial, not super interesting

// https://www.hackerrank.com/challenges/missing-numbers/problem
object Solution {
    def main(args: Array[String]) {
        val lines = scala.io.Source.stdin.getLines
        val (arr1, arr2) = (parseArray(lines.drop(1).next()), parseArray(lines.drop(1).next()))

        val multiset = new mutable.HashMap[Int, Int]()
        for (i <- arr2) multiset += i -> (multiset.getOrElse(i, 0) + 1)
        for (i <- arr1) multiset(i) match { case 1 => multiset -= i case v => multiset += i -> (v - 1) }

        println(multiset.keys.toArray.sorted.mkString(" "))
    }

    private def parseArray(s: String) = s.split(' ').map(_.toInt)
}
