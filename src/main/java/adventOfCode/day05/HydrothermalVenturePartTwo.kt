package adventOfCode.day05

// Difficulty: trivial: the first part is a superset of the second...

// https://adventofcode.com/2021/day/5#part2
fun main() {
    val vents = HydrothermalVenture.vents
    val matrix = initMatrix(vents)
    findIntersections(vents, matrix)
}

