package hackerrank.algorithms.sorting.fraudulentActivityNotifications

// Difficulty: I didn't think about bucket sorting until very late as I didn't properly read the applicable constraints.
//             With bucket sorting, it gets pretty easy though. Wonder if this is the most efficient way, my solution seems
//             quadratic to me (e.g. window = expenditure.length/2 = n/2, would need to iterate n/2 times over up to n/4 elements
//             to find the median at every step), but in practice it still performs slightly faster than my much more complicated
//             skiplist version. This implementation would never work eith a larger range of values though. Still a pretty neat
//             solution, nice problem.

// https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem
object Solution {
  private val MaxExpenditure = 200
    
  def activityNotifications(expenditure: Array[Int], window: Int): Int = {
    val buckets = Array.ofDim[Int](MaxExpenditure + 1)
    expenditure.view.take(window).foreach(i => buckets(i) += 1)
    
    var notifications = 0
    val halfWindow = window / 2
      
    for (i <- (window until expenditure.length).view) {
        val (spentFirstDay, spentToday) = (expenditure(i - window), expenditure(i))
        var (countBelowIndex, index, lower, higher) = (0, 0, -1, -1)
        
        while (lower < 0 || higher < 0) {
            countBelowIndex += buckets(index)
            if (lower < 0 && countBelowIndex > halfWindow - 1) lower = index
            if (higher < 0 && countBelowIndex > halfWindow) higher = index
            index += 1
        }
        
        buckets(spentFirstDay) -= 1
        buckets(spentToday) += 1
        
        val median = if (window % 2 == 1) higher else (lower + higher).toDouble / 2
        if (spentToday >= 2 * median) notifications += 1
    }
      
    notifications
  }
}

