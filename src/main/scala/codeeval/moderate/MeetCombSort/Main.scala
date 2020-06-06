package codeeval.moderate.MeetCombSort

object Main extends App {
    scala.io.Source.fromFile(args(0))
        .getLines()
        .map(_.split(' ').map(_.toInt))
        .map(iterateCombSort)
        .foreach(println)

    def iterateCombSort(arr: Array[Int]) = {
        val decreaseFactor           = 1.25
        var (iterations, hasSwapped) = (0, false)
        var gap                      = (arr.length / decreaseFactor).toInt

        while (gap > 0) {
            hasSwapped = false

            for (i <- 0 to arr.length - gap - 1) {
                if (arr(i) > arr(i + gap)) {
                    swap(arr, i, i + gap)
                    hasSwapped = true
                }
            }
            iterations += 1
            gap         = (gap / decreaseFactor).toInt
        }
        iterations - (if (hasSwapped) 0 else 1)
    }

    def swap(arr: Array[Int], i: Int, j: Int) = {
        val tmp = arr(i)
        arr(i)  = arr(j)
        arr(j)  = tmp
    }
}
