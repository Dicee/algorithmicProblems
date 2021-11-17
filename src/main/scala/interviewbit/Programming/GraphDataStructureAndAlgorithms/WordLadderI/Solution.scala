package interviewbit.Programming.GraphDataStructureAndAlgorithms.WordLadderI

import scala.collection.{View, mutable}

// Difficulty: easy, classic A*. Wrote in Scala despite Interwbit didn't support it for this question, because it's a lot more
//             elegant than the Java version

// https://www.interviewbit.com/problems/word-ladder-i/
object Solution {
  def main(args: Array[String]): Unit = {
    println(ladderLength("hit", "cog", Array("hot","dot","dog","lot","log"))) // 5
    println(ladderLength("aabbaaba", "aababaaa", Array(
      "aabbaaba", "aababaaa", "baabbaaa", "baabbbab", "bbbabbaa", "bbabbaba", "abbaabaa", "aabbbabb", "abababbb", "abaaabba", "bbbaaabb", "abbaaaab", "abababab", "abbbabab", "abaaaabb", "aaaaabaa",
      "baaaabaa", "bbabbabb", "ababaaab", "aabaabab", "babbbaba", "bbbaaabb", "babaaabb", "aabaaaab", "bbaabbaa", "aaababaa", "bbbbabab", "aaaababa", "bbbbbaba", "abaabaab",
      "baaababb", "bbabbaaa", "abbbbbab", "bbbbbabb", "abaaabaa", "babbaabb", "babaabab", "aabbbbba", "baabaabb"))) // 0
  }

  def ladderLength(start: String, end: String, dictionary: Array[String]): Int = {
    val nodes       = buildGraph(start, end, dictionary)

    val initial     = Path(0, nodes(start))
    val candidates  = mutable.PriorityQueue[Path](initial)(Ordering[Path].reverse)
    val optimalPath = mutable.HashMap[Node, Int](initial.node -> 0)

    while (candidates.nonEmpty) {
      val Path(dist, node) = candidates.dequeue()
      if (node == nodes(end)) return dist + 1 // total length of the path, not just the number of transitions
      for (neighbour <- node.neighbours) {
        val path = Path(dist + 1, neighbour)
        if (optimalPath.getOrElse(path.node, Int.MaxValue) > path.distance){
          candidates.enqueue(path)
          optimalPath += path.node -> path.distance
        }
      }
    }
    0
  }

  private def buildGraph(start: String, end: String, dictionary: Array[String]) = {
    val n        = dictionary.length + 2
    val words    = Array.ofDim[String](n)
    words(0    ) = start
    words(n - 1) = end
    dictionary.copyToArray(words, 1)

    val nodes = words.view.distinct.map(word => (word, Node(minDistance(word, end), word))).toMap

    // iterate over the upper triangle of the cartesian product while being careful not to
    // create an illegal links (e.g. cannot link a word in the dictionary to the start word
    // unless it belongs to the dictionary, whereas the opposite is always valid)
    for (i <- 0 until n - 1; j <- i + 1 until n; if minDistance(words(i), words(j)) == 1) {
      nodes(words(i)) link nodes(words(j))
      if (i > 0 && j < n - 1 ) nodes(words(j)) link nodes(words(i))
    }

    nodes
  }

  private def minDistance(w1: String, w2: String) = w1.view.zip(w2.view).count { case (ch1, ch2) => ch1 != ch2 }

  private case class Path(distance: Int, node: Node) extends Comparable[Path] {
    private lazy val totalDistance = distance + node.minDistanceToEnd
    override def compareTo(that: Path): Int = totalDistance compareTo that.totalDistance
  }

  private case class Node(minDistanceToEnd: Int, word: String) {
    private val _neighbours = mutable.HashSet[Node]()

    def link(node: Node): Unit = _neighbours += node
    def neighbours: View[Node] = _neighbours.view
  }
}
