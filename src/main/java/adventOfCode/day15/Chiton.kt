package adventOfCode.day15

import java.util.*
import java.util.Comparator.comparing
import kotlin.math.abs

// Difficulty: easy, classic A*

// https://adventofcode.com/2021/day/15
fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

private fun part1() = findMinPath(Chiton.riskLevels)

// it's easier to build the bigger grid than abstracting the graph in a class and calculating the coordinates in the bigger graph
// dynamically, and it's not much more costly in memory for the provided example (500^2 - 100^2 = (500-100)(500+100) = 240,000).
private fun part2(): Int {
    val topLeftTile = Chiton.riskLevels
    val (n, m) = topLeftTile.size to topLeftTile[0].size
    val grid = MutableList(n * 5) { MutableList(m * 5) { 0 } }

    for (i in grid.indices) {
        for (j in grid[0].indices) {
            val value = topLeftTile[i % n][j % m] + i / n + j / m
            grid[i][j] = value % 10 + value / 10
        }
    }

    return findMinPath(grid)
}

private fun findMinPath(grid: List<List<Int>>): Int {
    val start = Node(0, 0)
    val goal = Node(grid.size - 1, grid[0].size - 1)

    val visited = mutableSetOf(start)
    val minPathCosts = mutableMapOf(start to 0)
    val candidates = PriorityQueue(comparing(manhattanDistance(goal, minPathCosts)))
    candidates.add(start)

    while (candidates.isNotEmpty()) {
        val node = candidates.poll()!!
        val cost = minPathCosts[node]!!
        if (node == goal) return cost

        visited.add(node)

        for ((di, dj) in Chiton.directions) {
            val neighbour = Node(node.i + di, node.j + dj)
            val (ni, nj) = neighbour

            if (ni < 0 || nj < 0 || ni >= grid.size || nj >= grid[0].size) continue
            if (neighbour in visited) continue

            val nCost = cost + grid[ni][nj]
            if (nCost < (minPathCosts[neighbour] ?: Int.MAX_VALUE)) {
                minPathCosts[neighbour] = nCost
                candidates.add(neighbour)
            }
        }
    }

    throw IllegalStateException("Could not find a valid path to $goal")
}

// this heuristic is admissible because all values in the grid are greater than zero
private fun manhattanDistance(goal: Node, minPathCosts: Map<Node, Int>) =
        { node: Node -> minPathCosts[node]!! + abs(node.i - goal.i) + abs(node.j - goal.j) }

data class Node(val i: Int, val j: Int)

object Chiton {
    val riskLevels = Chiton::class.java.getResource("riskLevels.txt")!!.readText().lines().map { it.map(Char::digitToInt) }
    val directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
}