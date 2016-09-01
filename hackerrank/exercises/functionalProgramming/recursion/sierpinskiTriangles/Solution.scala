package hackerrank.exercises.functionalProgramming.recursion.sierpinskiTriangles

// Difficulty: moderate. Pretty classical but still requires coding.

// https://www.hackerrank.com/challenges/functions-and-fractals-sierpinski-triangles
object Solution {
    def main(args: Array[String]) {
        val n = scala.io.Source.stdin.getLines.next().toInt

        val (nRows    , nCols     ) = (32 , 63 )
        val (emptyChar, filledChar) = ('_', '1')

        val canvas = Array.fill[Char](nRows, nCols)(filledChar)
        for (i <- 0 until nRows - 1; j <- 0 until nCols / 2 - i) {
            canvas(i)(j            ) = emptyChar
            canvas(i)(nCols - 1 - j) = emptyChar
        }

        def subdivide(baseMid: (Int, Int), height: Int, depth: Int): Unit = {
            if (depth != 0) {
                val halvedHeight = height / 2
                for (i <- 0 until halvedHeight; j <- -i to i) canvas(baseMid._1 - i)(baseMid._2 + j) = emptyChar
                val subTriangle: ((Int, Int)) => Unit = subdivide(_, halvedHeight, depth - 1)
                subTriangle((baseMid._1 - halvedHeight, baseMid._2               ))
                subTriangle((baseMid._1               , baseMid._2 - halvedHeight))
                subTriangle((baseMid._1               , baseMid._2 + halvedHeight))
            }
        }

        subdivide((nRows - 1, nCols / 2), nRows, n)
        printMatrix(canvas)
    }

    def printMatrix[T](matrix: Array[Array[T]]) = {
        for (i <- matrix.indices) {
            for (j <- matrix(0).indices) print(matrix(i)(j))
            println()
        }
    }
}
