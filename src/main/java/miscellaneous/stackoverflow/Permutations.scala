package miscellaneous.stackoverflow

object Permutations {
  def sortedPermutations(list: List[Int]): List[List[Int]] = list match {
    case Nil | _ :: Nil => List(list)
    case _              => list.indices.flatMap(i => list.splitAt(i) match {
      case (head, t :: tail) => sortedPermutations(head ::: tail).map(t :: _)
    }).toList
  }
}
