package adventOfCode.day05

import kotlin.math.max
import kotlin.math.sign

// Difficulty: easy

// https://adventofcode.com/2021/day/5
fun main() {
    val vents = HydrothermalVenture.vents.filter { vent ->
        vent.from.first == vent.to.first || vent.from.second == vent.to.second
    }

    val matrix = initMatrix(vents)
    findIntersections(vents, matrix)
}

fun initMatrix(vents: List<Vent>): Array<Array<Int>> {
    val (w, h) = vents.fold(Pair(0, 0)) { (maxX, maxY), vent ->
        val newMaxI = max(max(maxX, vent.from.first), vent.to.first)
        val newMaxJ = max(max(maxY, vent.from.second), vent.to.second)
        Pair(newMaxI, newMaxJ)
    }
    return Array(h + 1) { Array(w + 1) { 0 } }
}

// once again, I'll just brute-force as the problem is small
fun findIntersections(vents: List<Vent>, matrix: Array<Array<Int>>) {
    val intersections = mutableSetOf<Pair<Int, Int>>()
    for (vent in vents) {
        for ((x, y) in vent.points()) {
            matrix[y][x]++
            if (matrix[y][x] > 1) intersections.add(Pair(y, x))
        }
    }

    println(intersections.size)
}

object HydrothermalVenture {
    val vents = HydrothermalVenture::class.java.getResource("vents.txt")!!
        .readText()
        .lineSequence()
        .map { it.split(" -> ") }
        .map { (from, to) -> Vent(parsePoint(from), parsePoint(to)) }
        .toList()

    private fun parsePoint(s: String): Pair<Int, Int> {
        val (i, j) = s.split(',')
        return Pair(i.toInt(), j.toInt())
    }
}

data class Vent(val from: Pair<Int, Int>, val to: Pair<Int, Int>) {
    fun points(): Sequence<Pair<Int, Int>> {
        val (incI, incJ) = Pair((to.first - from.first).sign, (to.second - from.second).sign)
        return generateSequence(0) { it + 1 }
            .map { step -> Pair(from.first + incI * step, from.second + incJ * step) }
            .takeWhile { it != to }
            .plusElement(to)
    }
}