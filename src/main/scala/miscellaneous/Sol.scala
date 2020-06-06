//package miscellaneous
//
//import scala.annotation.tailrec
//import scala.collection.immutable.TreeSet
//import scala.collection.mutable.ListBuffer

// https://www.hackerrank.com/challenges/reverse-shuffle-merge
//object Sol {
//  def main(args: Array[String]): Unit = {
////    println(reverseShuffleMerge("eggegg"))
////    println(reverseShuffleMerge("abcdefgabcdefg"))
////    println(reverseShuffleMerge("aeiouuoiea"))
//
//    println(reverseShuffleMerge("abcdefacdefgbg")) // gafedcb
//
//    // bgafdec
//  }
//
//  def reverseShuffleMerge(s: String): String = {
//    val occurrences   = s.groupBy(identity).mapValues(_.length)
//    val sortedIndices = s.view.distinct.sorted.zipWithIndex.toMap
//
//    @tailrec
//    def recSol(chars: List[Char], leftToAdd: MultiCharSet,indicesLeftToAdd: TreeSet[Int], leftAvailable: MultiCharSet,
//               skipped: TreeSet[Char], acc: ListBuffer[Char]): String = {
//      chars match {
//        case ch :: t =>
//          val index = sortedIndices(ch)
//          val optimal = indicesLeftToAdd.nonEmpty && indicesLeftToAdd.min == index
//
//          if (optimal || leftAvailable(ch) == leftToAdd(ch)) {
//            acc += ch
//
//            val newLeftToAdd = leftToAdd - ch
//            val fullyConsumed = newLeftToAdd(ch) == 0
//
//            if (!optimal) recSol(t, newLeftToAdd, if (fullyConsumed) indicesLeftToAdd - index else indicesLeftToAdd, leftAvailable - ch, acc)
//            else          recSol(t, newLeftToAdd, if (fullyConsumed) indicesLeftToAdd - index else indicesLeftToAdd, leftAvailable - ch, skipped, acc)
//
//          } else {
//            recSol(t, leftToAdd, indicesLeftToAdd, leftAvailable - ch, skipped + ch, acc)
//          }
//        case Nil    => acc.mkString
//      }
//    }
//    recSol(s.reverse.toList,
//      new MultiCharSet(occurrences.mapValues(_ / 2)),
//      TreeSet[Int]() ++ sortedIndices.values,
//      new MultiCharSet(occurrences), TreeSet(), ListBuffer())
//  }
//
//  private class MultiCharSet(counts: Map[Char, Int]) {
//    def apply(ch: Char) = counts.getOrElse(ch, 0)
//    def -(ch: Char)     = new MultiCharSet(counts + (ch -> (counts(ch) - 1)))
//  }
//}
