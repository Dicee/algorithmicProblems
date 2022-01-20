package adventOfCode.day1

// Difficulty: trivial

// https://adventofcode.com/2021/day/1
fun main() {
    val depths = SonarSweep.depths
    var counter = 0

    for (i in 1 until depths.size - 1) {
        if (depths[i - 1] < depths[i]) counter ++
    }

    println(counter)
}

object SonarSweep {
    val depths = SonarSweep::class.java.getResource("depths.txt")!!
            .readText()
            .trim()
            .lines()
            .map { it.toInt() }
}