package codeeval.moderate.GameOfLife

object Main extends App {
  val lines         = scala.io.Source.fromFile(args(0)).getLines().toArray
  val n            = lines.length
  var currentState = lines.map(_.toArray)
  var nextState    = Array.ofDim[Char](n, n)

  for (iteration <- 1 to 10) {
    for (i <- 0 until n; j <- 0 until n) {
      val neighboursCount = (for {
        dx <- -1 to 1
        dy <- -1 to 1
        if dx != 0 || dy != 0
        if 0 <= i + dx && i + dx < n
        if 0 <= j + dy && j + dy < n
      } yield (i + dx, j + dy)) count { case (ii, jj) => currentState(ii)(jj) == '*' }

      (currentState(i)(j), neighboursCount) match {
        case ('.', 3) | ('*', 2 | 3)                => nextState(i)(j) = '*'
        case ('*', count) if count < 2 || 3 < count => nextState(i)(j) = '.'
        case _                                      => nextState(i)(j) = currentState(i)(j)
      }
    }

    val tmp      = currentState
    currentState = nextState
    nextState    = tmp
  }

  print(currentState.map(_.mkString).mkString("\n"))
}
