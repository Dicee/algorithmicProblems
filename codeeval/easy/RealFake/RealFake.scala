package codeeval.easy.RealFake

object Main extends App {
    scala.io.Source.fromFile(args(0))
                   .getLines
                   .map(_.replace(" ", "").map(_.asDigit).zipWithIndex)
                   .map(_.map { case (x, i) => if (i % 2 == 0) 2 * x else x })
                   .map(numbers => if (numbers.sum % 10 == 0) "Real" else "Fake")
                   .foreach(println)
}