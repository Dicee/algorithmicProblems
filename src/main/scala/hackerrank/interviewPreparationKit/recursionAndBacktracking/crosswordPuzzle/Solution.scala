package hackerrank.interviewPreparationKit.recursionAndBacktracking.crosswordPuzzle

import scala.annotation.tailrec

// Difficulty: medium. The size of the problem means a brute force solution is enough to pass all tests, so in that sense the problem is easy.
//             Nevertheless, it requires a lot more code than a typical Hackerrank solution and it still took me several hours to get it done as
//             I used it as an occasion to write as idiomatic and elegant Scala as possible over my usual concerns of performance. I liked
//             being able to sacrifice some performance for pure immutability (except in the very last statement!) and elegance, for once!

// https://www.hackerrank.com/challenges/crossword-puzzle/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking
object Solution {
    import Step._
    private type Solution = Map[Point, Char]

    def crosswordPuzzle(crossword: Array[String], csvWords: String): Array[String] = {
        val n          = crossword.length
        val emptyCells = Set() ++ (for (i <- 0 until n; j <- 0 until n; if crossword(i)(j) == '-') yield Point(i, j))

        def backtracking(p: Point, remainingWords: Set[String], occupiedCells: Solution): Option[Solution] = {
            if (remainingWords.isEmpty && emptyCells == occupiedCells.keySet) Some(occupiedCells)
            else {
                val sol = remainingWords.view.flatMap(word => {
                    tryUsingWord(word, p, Horizontal, remainingWords, occupiedCells) orElse
                    tryUsingWord(word, p, Vertical , remainingWords, occupiedCells)
                }).headOption

                sol match {
                    case Some(_) => sol
                    case None    => next(p) match {
                        case Some(next) => backtracking(next, remainingWords, occupiedCells)
                        case None       => None
                    }
                }
            }
        }

        def tryUsingWord(word: String, p: Point, step: Step, remainingWords: Set[String], occupiedCells: Solution) =
            tryFitWord(word, p, step, occupiedCells) match {
                case Some(partialSol) => backtracking(p, remainingWords - word, partialSol)
                case None             => None
            }

        def tryFitWord(word: String, start: Point, step: Step, occupiedCells: Solution): Option[Solution] = {
            @tailrec
            def tryMatch(requiredCells: List[(Char, Point)], occupiedCells: Solution): Option[Solution] = requiredCells match {
                case (ch, point) :: t =>
                    if (!isValid(point)) None else occupiedCells.get(point) match {
                        case Some(x) if x == ch => tryMatch(t, occupiedCells)
                        case None               => tryMatch(t, occupiedCells + (point -> ch))
                        case _                  => None
                    }
                case Nil => Some(occupiedCells)
            }

            val requiredCells = word.view.zipWithIndex.map { case (ch, i) => (ch, start + step * i) }.toList
            tryMatch(requiredCells, occupiedCells)
        }

        @tailrec
        def next(p: Point): Option[Point] =
            if (p.i == n - 1 && p.i == p.j) None
            else {
              val q = if (p.j == n - 1) Point(p.i + 1, 0) else Point(p.i, p.j + 1)
              if (isFillable(q)) Some(q) else next(q)
            }

        def isValid   (p: Point) = p.i >= 0 && p.i < n && p.j >= 0 && p.j < n && isFillable(p)
        def isFillable(p: Point) = crossword(p.i)(p.j) == '-'

        val words       = Set(csvWords.split(";"): _*)
        val solution    = crossword.map(s => Array(s: _*))
        val filledCells = backtracking(Point(0, 0), words, Map()).get
        for ((Point(i, j), ch) <- filledCells) solution(i)(j) = ch

        solution.map(_.mkString)
    }

    // these immutable classes are not the most efficient way to achieve their purpose, but my solution is
    // a brute force algorithm anyway, so I figured I'd just have fun with Scala's DSL capabilities
    case class Point(i : Int, j : Int) { def +(step  : Step) = Point(i + step.di, j + step.dj) }
    case class Step (di: Int, dj: Int) { def *(lambda: Int ) = Step (lambda * di, lambda * dj) }

    object Step {
      val Horizontal = Step(0, 1)
      val Vertical = Step(1, 0)
    }
}
