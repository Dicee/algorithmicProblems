package unknownSource

object Pb5 extends App {
  def solution(arr: Array[Int]) = {
    val tickets = Array(Ticket(1, 2), Ticket(7, 7), Ticket(30, 24))
    val memoizedSols = scala.collection.mutable.Map[Int, Int]()

    def recSol(days: List[Int]): Int = if (days.isEmpty) 0 else
      memoizedSols.getOrElseUpdate(days.head, (for {
        ticket <- tickets
        remainingDays = daysAfterTicket(days, ticket)
        cost = ticket.cost + recSol(remainingDays)
      } yield cost).min)

    def daysAfterTicket(days: List[Int], ticket: Ticket) = days match {
      case Nil => Nil
      case firstDay :: q => days.dropWhile(_ - firstDay < ticket.duration)
    }

    recSol(arr.toList)
  }

  case class Ticket(duration: Int, cost: Int)

  assert(solution(Array(1)) == 2)
  assert(solution(Array(1, 2, 3)) == 6)
  assert(solution(Array(1, 2, 3, 8)) == 8)
  assert(solution(Array(1, 2, 3, 7)) == 7)
  assert(solution(Array(1, 2, 3, 7, 23)) == 9)
  assert(solution(Array(1, 2, 3, 7, 10, 11, 12, 23, 24, 25, 26)) == 20)
  assert(solution((1 to 24).toArray) == 24)
  assert(solution((1 to 21).toArray) == 21)
}
