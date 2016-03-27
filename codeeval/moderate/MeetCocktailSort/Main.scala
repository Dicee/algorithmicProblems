package codeeval.moderate.MeetCocktailSort

object Main extends App {
    scala.io.Source.fromFile(args(0))
        .getLines()
        .map(_.split(" \\| ") match { case Array(numbers, iterations) =>  (numbers.split(' ').map(_.toInt), iterations.toInt) })
        .map(t => iterateCocktailSort(t._1, t._2).mkString(" "))
        .foreach(println)

    def iterateCocktailSort(arr: Array[Int], iterations: Int) = {
        for (it <- 0 until iterations) {
            for (i <- it until arr.length - 1) {
                if (arr(i) > arr(i + 1)) swap(arr, i, i + 1)
            }

            for (i <- arr.length - 1 to it + 1 by -1) {
                if (arr(i - 1) > arr(i)) swap(arr, i - 1, i)
            }
        }
        arr
    }

    def swap(arr: Array[Int], i: Int, j: Int) = {
        val tmp = arr(i)
        arr(i)  = arr(j)
        arr(j)  = tmp
    }
}
