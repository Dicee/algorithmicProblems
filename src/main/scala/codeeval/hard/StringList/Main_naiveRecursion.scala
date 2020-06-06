package codeeval.hard.StringList

object Main_naiveRecursion extends App {
  scala.io.Source.fromFile(args(0)).getLines()
    .map(_.split(',') match { case Array(n, s) => (n.toInt, s.toSet.toSeq.sorted.toList) })
    .map { case (n, chars) => {
      def recSol(n: Int): List[List[Char]] = (chars, n) match {
        case (_  , 0) => List(Nil)
        case (Nil, _) => Nil
        case (_  , _) =>
          val tail = recSol(n - 1)
          chars.map(ch => tail.map(ch :: _)).fold(Nil)(_ ::: _)
      }
      recSol(n).map(_.mkString).mkString(",")
  }}
  .foreach(println)
}