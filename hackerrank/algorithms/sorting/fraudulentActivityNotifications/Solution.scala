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

    val dailySpend = expenditure.toList
    dailySpend.zip(dailySpend.drop(window)).foreach { case (spentFirstDay, spentToday) =>
      var (countBelowIndex, index) = (0, 0)
      while (countBelowIndex < halfWindow) {
        countBelowIndex += buckets(index)
        index += 1
      }

      val median = if (window % 2 == 1) index - 1 else (index - 1 + (if (countBelowIndex > halfWindow) index - 1 else index)).toDouble / 2
      if (spentToday >= 2 * median) notifications += 1

      buckets(spentFirstDay) -= 1
      buckets(spentToday) += 1
    }

    notifications
  }
}

