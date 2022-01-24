package adventOfCode.day11

// Difficulty: easy, and very cool to visualize this algorithm, as it made me remember about this BBC documentary where they explain
//             how some firefly species manage to synchronize their flashes: https://www.youtube.com/watch?v=wjMhrLBy4lY&t=345s. Really
//             enjoyed the problem!

// https://adventofcode.com/2021/day/11
fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

private fun part1(): Int {
    val energyLevels = DumboOctopus.energyLevels()
    return (1..100).sumOf { executeStep(energyLevels).size }
}

private fun part2(): Int {
    val energyLevels = DumboOctopus.energyLevels()
    val (n, m) = Pair(energyLevels.size, energyLevels[0].size)
    return generateSequence(0) { it + 1 }.indexOfFirst { executeStep(energyLevels).size == n * m } + 1
}

private fun executeStep(energyLevels: Array<Array<Int>>): MutableSet<Pair<Int, Int>> {
    val flashedOctopuses = mutableSetOf<Pair<Int, Int>>()
    for (i in energyLevels.indices) {
        for (j in energyLevels[0].indices) {
            incrementEnergyLevel(i, j, energyLevels, flashedOctopuses)
        }
    }
    println(energyLevels.joinToString("\n") { it.joinToString("") })
    println("=========")
    return flashedOctopuses
}

private fun incrementEnergyLevel(i: Int, j: Int, energyLevels: Array<Array<Int>>, flashedOctopuses: MutableSet<Pair<Int, Int>>) {
    if (energyLevels[i][j] == 9) triggerFlash(i, j, energyLevels, flashedOctopuses)
    else if ((i to j) !in flashedOctopuses) energyLevels[i][j]++
}

private fun triggerFlash(i: Int, j: Int, energyLevels: Array<Array<Int>>, flashedOctopuses: MutableSet<Pair<Int, Int>>) {
    flashedOctopuses.add(i to j)
    energyLevels[i][j] = 0

    for (di in -1..1) {
        for (dj in -1..1) {
            val (ni, nj) = Pair(i + di, j + dj)

            if (ni < 0 || nj < 0 || ni >= energyLevels.size || nj >= energyLevels[0].size) continue
            if (ni >= energyLevels.size || nj >= energyLevels[0].size) continue
            if (Pair(ni, nj) in flashedOctopuses) continue

            incrementEnergyLevel(ni, nj, energyLevels, flashedOctopuses)
        }
    }
}

object DumboOctopus {
    private val rawEnergyLevels = DumboOctopus::class.java.getResource("energyLevels.txt")!!.readText().lines()

    fun energyLevels() = rawEnergyLevels.map { it.map { ch -> ch.digitToInt() }.toTypedArray() }.toTypedArray()
}