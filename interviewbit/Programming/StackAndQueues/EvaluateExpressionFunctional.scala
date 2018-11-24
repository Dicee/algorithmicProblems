package interviewbit.Programming.StcksAndQueues

import scala.annotation.tailrec

// Difficulty: trivial. 

// https://www.interviewbit.com/problems/evaluate-expression/
object EvaluateExpressionFunctional {
  def main(args: Array[String]): Unit = {
    println(evalRPN(Array("2", "1", "+", "3", "*"))) // 9
    println(evalRPN(Array("4", "13", "5", "/", "+"))) // 6
  }

  def evalRPN(expr: Array[String]) = {
    @tailrec
    def recSol(expr: List[String], acc: List[Token]): Int = (expr, acc) match {
      case (_     , Num(i) :: Num(j) :: (op @ Op(_)) :: t) => recSol(expr, Num(op(i, j)) :: t  )
      case (h :: t, _                                    ) => recSol(t   , Token(h)      :: acc)
      case (Nil   , Num(n) :: Nil                        ) => n
      case _                                               => throw new IllegalArgumentException("Malformed expression")
    }
    recSol(expr.view.reverse.toList, Nil)
  }

  private trait Token
  private object Token {
    def apply(s: String) = s.toList match {
      case (op @ ('+' | '-' | '*' | '/')) :: Nil => Op(op     )
      case _                                     => Num (s.toInt)
    }
  }

  private case class Num (i : Int ) extends Token
  private case class Op  (op: Char) extends Token {
    def apply(i: Int, j: Int) = op match {
      case '+' => i + j
      case '-' => i - j
      case '*' => i * j
      case '/' => i / j
    }
  }
}
