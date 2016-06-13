package codeeval.hard.PrefixExpressions

object Main extends App {
  val plus  : (Double, Double) => Double = _ + _
  val minus : (Double, Double) => Double = _ - _
  val times : (Double, Double) => Double = _ * _
  val divide: (Double, Double) => Double = _ / _

  scala.io.Source.fromFile(args(0)).getLines()
    .map(_.split(' ').toList.span(!_(0).isDigit) match {
      case (unparsedOperators, unparsedOperands) =>
        val operators = unparsedOperators.map { case "+" => plus case "-" => minus case "*" => times case "/" => divide }
        val reversedOperands = unparsedOperands.map(_.toDouble).reverse

        def recSol(operators: List[(Double, Double) => Double], reversedOperands: List[Double]): Double = (operators, reversedOperands) match {
          case (op :: ops, operand :: operands) => op(recSol(ops, operands), operand)
          case (Nil, operand :: Nil)            => operand
          case _                                => throw new IllegalArgumentException("Unbalanced expression")
        }

        recSol(operators, reversedOperands).toInt
    })
    .foreach(println)
}
