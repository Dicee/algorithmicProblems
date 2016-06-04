package codility.lessons.indeedPrime2016

/**
 * Level: painless
 */
object DwarfsRafting {
  def parseCoordinatesList(s: String) = if (s.isEmpty) Set[(Int, Int)]() else s.split(' ').map(parseCoordinates).toSet
  def parseCoordinates    (s: String) = (s.substring(0, s.length - 1).toInt - 1, s.charAt(s.length - 1) - 'A')

  def solution(n: Int, barrelsString: String, dwarfsString: String): Int = {
    val barrels = parseCoordinatesList(barrelsString)
    val dwarfs  = parseCoordinatesList(dwarfsString)

    def stateForSlice(topLeft: (Int, Int)) = {
      val length = n / 2
      val slice  = (for (i <- topLeft._1 until topLeft._1 + length; j <- topLeft._2 until topLeft._2 + length) yield (i, j)).toSet
      val existingDwarfs = (slice & dwarfs).size
      val barrelsCount   = (slice & barrels).size

      State(existingDwarfs, slice.size - existingDwarfs - barrelsCount)
    }

    val topLeft     = stateForSlice(0, 0)
    val topRight    = stateForSlice(0, n/2)
    val bottomRight = stateForSlice(n/2, n/2)
    val bottomLeft  = stateForSlice(n/2, 0)

    // simple maths show that top-left = bottom-right and top-right = bottom-left
    val diagonalBalancedTotal     = findBalancedTotal(topLeft, bottomRight)
    val antiDiagonalBalancedTotal = findBalancedTotal(topRight, bottomLeft)

    if (diagonalBalancedTotal < 0 || antiDiagonalBalancedTotal < 0) -1
    else diagonalBalancedTotal + antiDiagonalBalancedTotal - List(topLeft, topRight, bottomRight, bottomLeft).map(_.existingDwarfs).sum
  }

  def findBalancedTotal(state1: State, state2: State) = {
    val balance = Math.min(state1.maxCapacity, state2.maxCapacity)
    if (balance < Math.max(state1.existingDwarfs, state2.existingDwarfs)) -1
    else                                                                   2 * balance
  }

  case class State(existingDwarfs: Int, seatsAvailable: Int) {
    val maxCapacity = existingDwarfs + seatsAvailable
  }
}
