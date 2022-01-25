package adventOfCode.day12

// Difficulty: easy, basically a slightly modified DFS. Still was fun to write in a way that reuses code for both parts and reads nicely.

// https://adventofcode.com/2021/day/12
fun main() {
    val graph = PassagePathing.graph()
    println("Part 1: ${countPaths(PassagePathing.START, graph, ExploredSmallCaves())}")
    println("Part 2: ${countPaths(PassagePathing.START, graph, ExploredSmallCaves(allowExploringTwice = true))}")
}

private fun countPaths(cave: String, graph: Graph, explored: ExploredSmallCaves, path: String = "", debug: Boolean = false): Int {
    val newPath = "$path-$cave"
    if (cave == PassagePathing.END) {
        if (debug) println(newPath.drop(1))
        return 1
    }

    if (cave.isSmallCave()) explored.add(cave)
    // assuming there is no isolated cave
    val count = graph[cave]!!.filter { explored.canAdd(it) }.sumOf { countPaths(it, graph, explored, newPath, debug) }
    explored.remove(cave)

    return count
}

class ExploredSmallCaves(private val allowExploringTwice: Boolean = false) {
    val explored: MutableSet<String> = mutableSetOf()
    private var addedTwice: String? = null

    fun canAdd(cave: String): Boolean {
        return (allowExploringTwice && addedTwice == null && !cave.isTerminalCave()) || cave !in explored
    }

    fun add(cave: String) {
        val added = explored.add(cave)
        if (!added) addedTwice = cave
    }

    fun remove(cave: String) {
        if (addedTwice == cave) addedTwice = null
        else explored.remove(cave)
    }
}

fun String.isSmallCave() = this.all { it.isLowerCase() }
fun String.isTerminalCave() = this == PassagePathing.START || this == PassagePathing.END

typealias Graph = Map<String, List<String>>

object PassagePathing {
    const val START = "start"
    const val END = "end"

    fun graph(): Graph {
        val graph = mutableMapOf<String, MutableList<String>>()
        PassagePathing::class.java.getResource("graph.txt")!!.readText().lines()
            .map { it.split('-') }
            .forEach { (from, to) ->
                graph.merge(from, mutableListOf(to)) { old, new -> old.addAll(new); old }
                graph.merge(to, mutableListOf(from)) { old, new -> old.addAll(new); old }
            }

        return graph
    }
}