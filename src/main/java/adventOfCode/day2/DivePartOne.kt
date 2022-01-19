package adventOfCode.day2

// Difficulty: trivial

// https://adventofcode.com/2021/day/2
fun main() {
    val finalState = Dive.instructions.asSequence()
        .map { instruction ->
            val (action, step) = instruction.split(' ')
            when (action) {
                "forward" -> { pos:Int, depth: Int -> Pair(pos + step.toInt(), depth) }
                "up" -> { pos: Int, depth: Int -> Pair(pos, depth - step.toInt()) }
                else -> { pos: Int, depth: Int -> Pair(pos, depth + step.toInt()) }
            }
        }
        .fold(Pair(0, 0)) { acc, instruction -> instruction(acc.first, acc.second) }

    println(finalState.first * finalState.second)
}

object Dive {
    val instructions = Dive::class.java.getResource("instructions.txt")!!
        .readText()
        .split('\n')
}