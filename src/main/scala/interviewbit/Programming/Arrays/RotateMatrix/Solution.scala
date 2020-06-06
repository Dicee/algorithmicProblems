package interviewbit.Programming.Arrays.RotateMatrix

// Difficulty: easy, but it seems like I overcomplicated it. The editorial solution looks a lot simpler although it's basically doing the same
//             i.e. rotating groups of 4 elements. Also, I hate Interviewbit for not always supporting Scala.

// https://www.interviewbit.com/problems/rotate-matrix/
object Solution {
  def main(args: Array[String]): Unit = {
//    7 4 1
//    8 5 2
//    9 6 3
    println(rotate(Array(
      Array(1, 2, 3),
      Array(4, 5, 6),
      Array(7, 8, 9)
    )).map(_.mkString(" ")).mkString("\n"))

//    13  9  5  1
//    14 10  6  2
//    15 11  7  3
//    16 12  8  4
    println(rotate(Array(
      Array(1, 2, 3, 4),
      Array(5, 6, 7, 8),
      Array(9, 10, 11, 12),
      Array(13, 14, 15, 16)
    )).map(_.map(i => "%2d".format(i)).mkString(" ")).mkString("\n"))
  }

  def rotate(matrix: Array[Array[Int]]) = {
    val n = matrix.length

    // rotate concentric squares of decreasing width to allow rotating the whole image
    // without using any buffer
    for (width <- n to 2 by -2) {
      val padding = (n - width) / 2

      for (j <- 0 until width - 1) {
        val firstCell = (0, j)
        var cell      = firstCell
        var displaced = matrix(cell._1 + padding)(cell._2 + padding)

        do {
          val nextCell      = move(cell, width)
          val nextDisplaced = matrix(nextCell._1 + padding)(nextCell._2 + padding)
          matrix(nextCell._1 + padding)(nextCell._2 + padding) = displaced

          cell      = nextCell
          displaced = nextDisplaced
        } while (cell != firstCell)
      }
    }

    matrix
  }

  private def move(cell: (Int, Int), w: Int) = {
    val (i, j) = cell

    if      (i == 0    ) (j    , w - 1    )
    else if (i == w - 1) (j    , 0        )
    else if (j == 0    ) (0    , w - 1 - i)
    else                 (w - 1, w - 1 - i)
  }
}
