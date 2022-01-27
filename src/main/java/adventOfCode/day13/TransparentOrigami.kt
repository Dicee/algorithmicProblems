package adventOfCode.day13

import kotlin.math.max

// Difficulty: easy and fun, really well done problem ^^

// https://adventofcode.com/2021/day/13
fun main() {
    val foldingInstructions = TransparentOrigami.foldingInstructions()
    println("Part 1: ${part1(foldingInstructions)}")
    println("Part 2: \n${part2(foldingInstructions)}")
}

private fun part1(instructions: FoldingInstructions) = applyFolds(instructions.copy(folds = instructions.folds.take(1))).size

private fun part2(instructions: FoldingInstructions): String {
    val newPoints = applyFolds(instructions)
    val (m, n) = newPoints.fold(0 to 0) { (maxX, maxY), p -> Pair(max(maxX, p.x + 1), max(maxY, p.y + 1)) }
    val matrix = Array(n) { Array(m) { '.' } }
    for (p in newPoints) matrix[p.y][p.x] = '#'
    return matrix.joinToString("\n") { it.joinToString(" ") }
}

private fun applyFolds(instructions: FoldingInstructions): Set<Point> {
    val foldingFunction = instructions.folds.fold({ p: Point -> p }) { acc, fold -> { p: Point -> acc(p).image(fold) } }
    return instructions.points.asSequence().map(foldingFunction).toSet()
}

data class FoldingInstructions(val points: List<Point>, val folds: List<Fold>)
data class Point(val x: Int, val y: Int) {
    fun image(fold: Fold) = when (fold) {
        is HorizontalFold -> if (x < fold.x) this else Point(2 * fold.x - x, y)
        is VerticalFold   -> if (y < fold.y) this else Point(x, 2 * fold.y - y)
    }
}

sealed interface Fold
data class HorizontalFold(val x: Int) : Fold
data class VerticalFold(val y: Int) : Fold

object TransparentOrigami {
    fun foldingInstructions(): FoldingInstructions {
        val (rawPoints, rawFolds) = TransparentOrigami::class.java.getResource("foldingInstructions.txt")!!.readText()
            .lineSequence()
            .filter { it.isNotBlank() }
            .partition { !it.startsWith("fold") }

        val points = rawPoints.map { it.split(',') }.map { (x, y) -> Point(x.toInt(), y.toInt()) }
        val folds = rawFolds
            .map { it.substring("fold along ".length).split('=') }
            .map { (axis, value) -> if (axis == "x") HorizontalFold(value.toInt()) else VerticalFold(value.toInt()) }

        return FoldingInstructions(points, folds)
    }
}