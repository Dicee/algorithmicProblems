package codeeval.hard.LargestSubMatrix

object Main_naive extends App {
  val matrix = scala.io.Source.fromFile(args(0)).getLines().map(_.split(' ').map(_.toInt)).toArray
  val sums   = Array.ofDim[Int](matrix.length, matrix(0).length)

  for (i <- 0 until matrix.length; j <- 0 until matrix(0).length) {
    sums(i)(j) = matrix(i)(j) + sumsOrZero(i - 1, j) + sumsOrZero(i, j - 1) - sumsOrZero(i - 1, j - 1)
  }

  def blockSum(bottomRight: (Int, Int), width: Int, height: Int) = {
    val topLeft    = (bottomRight._1 - width, bottomRight._2 - height)
    val topRight   = (bottomRight._1        , bottomRight._2 - height)
    val bottomLeft = (bottomRight._1 - width, bottomRight._2         )

    sumsOrZero(bottomRight) - sumsOrZero(bottomLeft) - sumsOrZero(topRight) + sumsOrZero(topLeft)
  }

  def sumsOrZero(coords: (Int, Int)): Int =
    if (coords._1 < 0 || coords._1 >= sums.length || coords._2 < 0 || coords._2 >= sums(0).length) 0
    else sums(coords._1)(coords._2)

  var max = Int.MinValue
  for {
    i <- 0 until matrix.length
    j <- 0 until matrix(0).length
    w <- 1 to i + 1
    h <- 1 to j + 1
  } max = Math.max(max, blockSum((i, j), w, h))

  println(max)
}
