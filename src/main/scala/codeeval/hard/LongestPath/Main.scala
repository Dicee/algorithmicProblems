package codeeval.hard.LongestPath

object Main extends App {
  scala.io.Source.fromFile(args(0)).getLines()
    .map(line => {
      val n      = Math.sqrt(line.length).toInt
      val matrix = line.grouped(n).map(_.toArray).toArray

      val memoized = scala.collection.mutable.HashMap[(Int, Int, Set[Char]), Int]()

      def recSol(i: Int, j: Int, explored: Set[Char]): Int = {
        memoized.getOrElseUpdate((i, j, explored), {
          val explorations = List(
            attemptToExplore(i - 1, j    , explored),
            attemptToExplore(i + 1, j    , explored),
            attemptToExplore(i    , j - 1, explored),
            attemptToExplore(i    , j + 1, explored)
          )

          if (explorations.forall(_ == 0)) explored.size else explorations.max
        })
      }

      def attemptToExplore(i: Int, j: Int, explored: Set[Char]): Int = {
        if (i < 0 || j < 0 || n <= i || n <= j || explored.contains(matrix(i)(j))) 0
        else recSol(i, j, explored + matrix(i)(j))
      }

      /*
       * Pretty brutal, but n is between 2 and 6 so... very small problem. The size alone makes it no fit for the
       * "hard" category.
       */
      (for (i <- 0 until n; j <- 0 until n) yield recSol(i, j, Set(matrix(i)(j)))).max
    }).foreach(println)
}
