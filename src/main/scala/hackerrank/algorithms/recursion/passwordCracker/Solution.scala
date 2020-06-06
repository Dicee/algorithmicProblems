package hackerrank.algorithms.recursion.passwordCracker

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// Difficulty: hard. The solution is simple, but it admittedly took me a while to get there. I tried another recursive
//             formulation for a while which made it really hard to memoize and thus passing test cases with lots of
//             backtracking. My original code structure was also slightly more complicated and confusing than the final
//             one, which didn't help.

// https://www.hackerrank.com/challenges/password-cracker/problem
object Solution {
  def main(args: Array[String]) {
    val lines     = scala.io.Source.stdin.getLines()
    val testCases = lines.next().toInt

    for (_ <- 1 to testCases) {
      val passwords    = lines.drop(1).next().split(' ').map(_.toList)
      val loginAttempt = lines.next()

      // List.length is expensive, but so is List.hashCode, and storing lists requires more memory than storing ints.
      // Therefore, we'll just store the length of all explored suffixes of login attempt
      val explored = new mutable.HashSet[Int]()

      def recSol(loginAttempt: List[Char], knownPasswords: Array[List[Char]], sol: ListBuffer[List[Char]]): Boolean = {
        if (explored contains loginAttempt.length) return false

        for (password <- knownPasswords) {
          val (prefix, suffix) = loginAttempt.splitAt(password.length)
          if (prefix == password) {
            sol += password
            if (suffix.isEmpty || recSol(suffix, knownPasswords, sol)) return true
            explored += suffix.length
            sol.remove(sol.length - 1)
          }
        }

        false
      }

      val sol = ListBuffer[List[Char]]()
      if (recSol(loginAttempt.toList, passwords, sol)) println(sol.map(_.mkString).mkString(" "))
      else println("WRONG PASSWORD")
    }
  }
}
