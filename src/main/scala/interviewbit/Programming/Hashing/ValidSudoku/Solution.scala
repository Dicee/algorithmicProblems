package interviewbit.Programming.Hashing.ValidSudoku

import scala.collection.mutable

// Difficulty: trivial

// https://www.interviewbit.com/problems/valid-sudoku/
object Solution {
  def main(args: Array[String]): Unit = {
    println(isValidSudoku(Array(
      "53..7....",
      "6..195...",
      ".98....6.",
      "8...6...3",
      "4..8.3..1",
      "7...2...6",
      ".6....28.",
      "...419..5",
      "....8..79"
    ))) // 1
  }

  def isValidSudoku(lines: Array[String]) = {
    def isSelectionValid(selectedCells: Seq[Char]): Boolean = {
      val chars = mutable.HashSet[Char]()
      for (cell <- selectedCells) if (cell != '.' && !chars.add(cell)) return false
      true
    }

    def row   (i: Int        ) = for (j <- lines(i).indices) yield lines(i)(j)
    def column(        j: Int) = for (i <- lines   .indices) yield lines(i)(j)
    def square(i: Int, j: Int) = for (di <- 0 until 3; dj <- 0 until 3) yield lines(i + di)(j + dj)

    val blocks =
      lines.indices.view.map(row) ++
      lines(0).indices.view.map(column) ++
      (for (i <- (0 until 3).view; j <- 0 until 3) yield square(3 * i, 3 * j))

    if (blocks.forall(isSelectionValid)) 1 else 0
  }
}
