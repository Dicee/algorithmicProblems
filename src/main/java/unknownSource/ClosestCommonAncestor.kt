package unknownSource

fun main() {
    val hierarchy = Node("sleb", setOf(
            Node("alberto", setOf(Node("courtino"), Node("dao"), Node("lioese"))),
            Node("guzima", setOf(
                    Node("bojdri", setOf(Node("sahra"), Node("nidriss"))),
                    Node("parishkamal"),
                    Node("braccio", setOf(Node("costaril"), Node("milotta"))),
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

private fun findClosestCommonAncestor(id1: String, id2: String, root: Node): String {
    val lineages = collectLineages(id1, id2, Lineage(root), mutableListOf())

    // both ids are equal so they have the same lineage
    if (lineages.size == 1) return lineages[0].node.id

    var (l1, l2) = lineages
    while (l1.depth != l2.depth) {
        if (l1.depth > l2.depth) l1 = l1.parent!!
        else l2 = l2.parent!!
    }

    while (l1.node.id != l2.node.id) {
        l1 = l1.parent!!
        l2 = l2.parent!!
    }

    return l1.node.id
}

private fun collectLineages(id1: String, id2: String, lineage: Lineage, acc: MutableList<Lineage>): List<Lineage> {
    val id = lineage.node.id
    // if the parent id is null it means the lineage is the root, and we consider it's its own parent
    if (id == id1 || id == id2) acc += lineage.parent ?: lineage

    val isSearchOver = acc.size == 2 || acc.size == 1 && id1 == id2
    if (!isSearchOver) {
        for (child in lineage.node.children) {
            collectLineages(id1, id2, Lineage(child, lineage), acc)
        }
    }
    return acc
}

data class Lineage(val node: Node, val parent: Lineage? = null) {
    val depth: Int = (parent?.depth ?: -1) + 1
}

data class Node(val id: String, val children: Set<Node> = setOf())
