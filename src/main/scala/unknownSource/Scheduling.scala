package unknownSource

object Scheduling {
  def main(args: Array[String]): Unit = {
    // too lazy to put actual timestamps
    println(maxCapacity(List(
      Activity(8, 9, "A", 6),
      Activity(9, 10, "B", 10),
      Activity(8, 11, "C", 8),
      Activity(10, 11, "A", 1),
    ))) // 18
  }

  def maxCapacity(activities: Seq[Activity]) = {
    activities
      .flatMap { case Activity(s, e, _, usedCapacity) => List(UsageStart(s, usedCapacity), UsageEnd(e, usedCapacity)) }
      .sortBy(_.timestamp)
      .scanLeft(0) {
        case (acc, UsageStart(_, usedCapacity)) => acc + usedCapacity
        case (acc, UsageEnd(_, usedCapacity)) => acc - usedCapacity
      }.max
  }

  case class Activity(start: Long, end: Long, name: String, usedCapacity: Int)

  sealed trait CapacityUsageEvent {
    val timestamp: Long
  }
  case class UsageStart(timestamp: Long, usedCapacity: Int) extends CapacityUsageEvent
  case class UsageEnd(timestamp: Long, usedCapacity: Int) extends CapacityUsageEvent
}
