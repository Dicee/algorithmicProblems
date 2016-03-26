package codeeval.easy.PanaceaTruthOrLie

object Main extends App {
    scala.io.Source.fromFile(args(0))
        .getLines()
        .map(_.split(" \\| ") match { case Array(left, right) => sumComponents(16, left) <= sumComponents(2, right)})
        .foreach(b => println(if (b) "True" else "False"))

    def sumComponents(base: Int, components: String) = components.split("\\s+").map(Integer.parseInt(_, base)).sum
}
