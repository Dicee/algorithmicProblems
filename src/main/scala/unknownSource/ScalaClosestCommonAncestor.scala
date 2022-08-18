package unknownSource

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object ScalaClosestCommonAncestor {
  def main(args: Array[String]): Unit = {
    val hierarchy = Node("sleb", Set(
      Node("alberto", Set(Node("courtino"), Node("dao"), Node("lioese"))),
      Node("guzima", Set(
        Node("bojdri", Set(Node("sahra"), Node("nidriss"))),
        Node("parishkamal"),
        Node("braccio", Set(Node("costaril"), Node("milotta"))),
      ))
    ))

    println(findClosestCommonAncestor("sleb", "sleb", hierarchy)) // sleb
    println(findClosestCommonAncestor("alberto", "sleb", hierarchy)) // sleb
    println(findClosestCommonAncestor("alberto", "courtino", hierarchy)) // sleb
    println(findClosestCommonAncestor("parishkamal", "courtino", hierarchy)) // sleb
    println(findClosestCommonAncestor("courtino", "dao", hierarchy)) // alberto
    println(findClosestCommonAncestor("parishkamal", "bojdri", hierarchy)) // guzima
    println(findClosestCommonAncestor("parishkamal", "costaril", hierarchy)) // guzima
    println(findClosestCommonAncestor("milotta", "costaril", hierarchy)) // braccio
    println(findClosestCommonAncestor("milotta", "braccio", hierarchy)) // guzima
    println(findClosestCommonAncestor("milotta", "parishkamal", hierarchy)) // guzima
    println(findClosestCommonAncestor("milotta", "alberto", hierarchy)) // sleb
    println(findClosestCommonAncestor("sahra", "nidriss", hierarchy)) // bojdri
  }

  private def findClosestCommonAncestor(id1: String, id2: String, root: Node): String = {
    collectLineages(id1, id2, Lineage(root), ArrayBuffer()) match {
      // both ids are equal so they have the same lineage
      case mutable.Seq(lineage) => lineage.node.id
      case mutable.Seq(l1, l2) => findClosestCommonAncestor(l1, l2)
    }
  }

  private def collectLineages(id1: String, id2: String, lineage: Lineage, acc: ArrayBuffer[Lineage]): mutable.Seq[Lineage] = {
    val id = lineage.node.id
    // if the parent id is null it means the lineage is the root, and we consider it's its own parent
    if (id == id1 || id == id2) acc += lineage.parent.getOrElse(lineage)

    val isSearchOver = acc.size == 2 || acc.size == 1 && id1 == id2
    if (!isSearchOver) {
      for (child <- lineage.node.children) {
        collectLineages(id1, id2, Lineage(child, Some(lineage)), acc)
      }
    }

    acc
  }

  @tailrec private def findClosestCommonAncestor(l1: Lineage, l2: Lineage): String = l1.depth - l2.depth match {
    case 0 => if (l1.node.id == l2.node.id) l1.node.id else findClosestCommonAncestor(l1.parent.get, l2.parent.get)
    case x if x > 0 => findClosestCommonAncestor(l1.parent.get, l2)
    case x if x < 0 => findClosestCommonAncestor(l1, l2.parent.get)
  }

  case class Lineage(node: Node, parent: Option[Lineage] = None) {
    val depth: Int = parent.map(_.depth + 1).getOrElse(0)
  }

  case class Node(id: String, children: Set[Node] = Set())
}
