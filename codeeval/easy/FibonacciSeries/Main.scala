import scala.io.Source;

object Main extends App {
    val indexes = (for (line <- Source.fromFile(args(0)).getLines) yield line.toInt).toIndexedSeq
    val max     = indexes.max

    lazy val fibo : Stream[Int] = 0 #:: fibo.zip(1 #:: fibo).map { case (x,y) => x + y }
    val      take               = fibo.take(max + 1).toArray

    for (i <- indexes) println(take(i))
}