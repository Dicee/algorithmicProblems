package adventOfCode.day02

// Difficulty: trivial

// https://adventofcode.com/2021/day/2#part2
fun main() {
    val finalState = Dive.instructions.asSequence()
        .map { instruction ->
            val (action, rawStep) = instruction.split(' ')
            val step = rawStep.toInt()

            when (action) {
                "forward" -> { pos:Int, depth: Int, aim: Int -> Triple(pos + step, depth + step * aim, aim) }
                "up" -> { pos: Int, depth: Int, aim: Int -> Triple(pos, depth, aim - step) }
                else -> { pos: Int, depth: Int, aim: Int -> Triple(pos, depth, aim + step) }
            }
        }
        .fold(Triple(0, 0, 0)) { acc, instruction -> instruction(acc.first, acc.second, acc.third) }

    println(finalState.first * finalState.second)
}