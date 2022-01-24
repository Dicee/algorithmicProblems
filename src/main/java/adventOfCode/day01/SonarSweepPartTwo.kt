package adventOfCode.day01

// Difficulty: trivial

// https://adventofcode.com/2021/day/1#part2
fun main() {
    val depths = SonarSweep.depths.asSequence()
        .windowed(3, partialWindows = true)
        .map { it.sum() }
        .toList()

    var counter = 0

    for (i in 1 until depths.size - 1) {
        if (depths[i - 1] < depths[i]) counter ++
    }

    println(counter)
}