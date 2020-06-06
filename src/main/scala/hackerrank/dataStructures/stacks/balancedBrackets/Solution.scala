package hackerrank.dataStructures.stacks.balancedBrackets

import scala.annotation.tailrec

// Difficulty: easy. I wanted to write a functional version rather than using a stack, and I think it look longer than it should but couldn't find
//             how to compress it after more than time than I'm willing to spend on this proble.

// https://www.hackerrank.com/challenges/balanced-brackets/problem
object Solution {
    private val BalancedPairs = Map('(' -> ')', '{' -> '}', '[' -> ']')
    private val OpeningChars =  BalancedPairs.keySet
    private val ClosingChars = Set() ++ BalancedPairs.values
    
    def isBalanced(s: String): String = {
        @tailrec
        def recSol(expr: List[Char]): String = expr match {
            case Nil => "YES"
            case _   => dropBalancedPrefix(expr) match {
                case Some(rest) => recSol(rest)
                case None       => "NO"
            }
        }
        recSol(s.toList)
    }

    private def dropBalancedPrefix(expr: List[Char]): Option[List[Char]] = expr match {
        case o :: c :: t if OpeningChars.contains(o) && BalancedPairs(o) == c => Some(t)
        case c :: t if ClosingChars.contains(c) => None
        case o :: t => dropUntil(t, BalancedPairs(o)) match {
            case Some(rest) => Some(rest)
            case None       => None
        }
        case Nil => Some(Nil)
    }

    private def dropUntil(expr: List[Char], closingChar: Char): Option[List[Char]] = dropBalancedPrefix(expr) match {
        case Some(c :: rest) if c == closingChar => Some(rest)
        case Some(t :: q) => dropUntil(t :: q, closingChar)
        case None | Some(Nil) => None
    }
}
