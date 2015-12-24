package codeeval.easy.Testing

object Main extends App {
    val levels = Array("Done", "Low", "Medium", "High", "Critical")
    def rectifyIndex(index: Int, length: Int) = Math.max(Math.min(index, length - 1), 0)
    scala.io.Source.fromFile("C:\\Users\\Dici\\Desktop\\input.txt")
                   .getLines
                   .map(_.split('|').map(_.trim))
                   .map(split => split(0).zip(split(1)).count(t => t._1 != t._2))
                   .foreach(count => println(levels(rectifyIndex((count + 1) / 2, levels.length))))
}