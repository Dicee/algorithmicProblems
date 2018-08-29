package codility.lessons.indeedPrime2016

/**
 * Level: painless
 */
object DwarfsRafting {
  def solution(n: Int, barrelsString: String, dwarfsString: String): Int = {
    val barrels = parseCoordinatesList(barrelsString)
    val dwarfs  = parseCoordinatesList(dwarfsString)

    def quarter(topLeft: (Int, Int)) = {
      val (x, y) = topLeft
      val slice  = (for (i <- x until x + n/2; j <- y until y + n/2) yield (i, j)).toSet
      
      val existingDwarfs = (slice & dwarfs).size
      val barrelsCount   = (slice & barrels).size

      Quarter(existingDwarfs, slice.size - barrelsCount)
    }
    
    // simple maths show that top-left = bottom-right and top-right = bottom-left
    val onboardedOnDiagonal     = maxOnboardable(quarter(0, 0), quarter(n/2, n/2))
    val onboardedOnAntiDiagonal = maxOnboardable(quarter(0, n/2), quarter(n/2, 0))
    if (onboardedOnDiagonal < 0 || onboardedOnAntiDiagonal < 0) -1 else onboardedOnDiagonal + onboardedOnAntiDiagonal
  }

  private def parseCoordinatesList(s: String) = if (s.isEmpty) Set[(Int, Int)]() else s.split(' ').map(parseCoordinates).toSet
  private def parseCoordinates    (s: String) = (s.substring(0, s.length - 1).toInt - 1, s.last - 'A')
  
  private def maxOnboardable(q1: Quarter, q2: Quarter) = {
    val maxBalancedCapacity = Math.min(q1.maxCapacity, q2.maxCapacity)
    val minBalancedCapacity = Math.max(q1.existingDwarfs, q2.existingDwarfs)
    if (maxBalancedCapacity < minBalancedCapacity) -1 else 2 * maxBalancedCapacity - q1.existingDwarfs - q2.existingDwarfs
  }
  
  private case class Quarter(existingDwarfs: Int, maxCapacity: Int)
}
