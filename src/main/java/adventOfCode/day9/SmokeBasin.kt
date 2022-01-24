package adventOfCode.day9

// Difficulty: easy.

// https://adventofcode.com/2021/day/9
fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

private fun part1(): Int {
    val heightMap = SmokeBasin.heightMap
    return cartesianProduct(heightMap.indices, heightMap[0].indices)
        .filter { (i, j) -> neighbours(i, j, heightMap).all { (ni, nj) -> heightMap[ni][nj] > heightMap[i][j] } }
        .sumOf { (i, j) -> heightMap[i][j].digitToInt() + 1 }
}

private fun part2(): Int {
    val heightMap = SmokeBasin.heightMap
    val visited = mutableSetOf<Pair<Int, Int>>()
    return cartesianProduct(heightMap.indices, heightMap[0].indices)
        .filter { shouldVisit(it.first, it.second, visited) }
        .map { findBasinSize(it.first, it.second, visited) }
        .sortedByDescending { it }
        .take(3)
        .reduce { acc, n -> acc * n }
}

private fun findBasinSize(i: Int, j: Int, visited: MutableSet<Pair<Int, Int>>): Int {
    if (!shouldVisit(i, j, visited)) return 0

    visited.add(i to j)
    return 1 + neighbours(i, j, SmokeBasin.heightMap).sumOf { findBasinSize(it.first, it.second, visited) }
}

private fun shouldVisit(i: Int, j: Int, visited: MutableSet<Pair<Int, Int>>) = SmokeBasin.heightMap[i][j] != '9' && (i to j) !in visited

private fun neighbours(i: Int, j: Int, heightMap: List<String>): Sequence<Pair<Int, Int>> {
    val (n, m) = heightMap.size to heightMap[0].length
    return cartesianProduct(-1..1)
        .filter { (di, dj) -> (di == 0) xor (dj == 0) }
        .filter { (di, dj) -> i + di >= 0 && j + dj >= 0 }
        .filter { (di, dj) -> i + di < n && j + dj < m }
        .map { (di, dj) -> (i + di) to (j + dj) }
}

private fun cartesianProduct(range: IntRange) = cartesianProduct(range, range)
private fun cartesianProduct(left: IntRange, right: IntRange) = left.asSequence().flatMap { i -> right.map { j -> i to j } }

object SmokeBasin {
    val heightMap = SmokeBasin::class.java.getResource("heightMap.txt")!!.readText().lines()
}