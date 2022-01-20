package adventOfCode.day6

// Difficulty: easy. That's the first AoC problem so far where I applied some "smarts" to avoid blowing up memory and time complexity,
//             so it's also a funnier one to do.

// https://adventofcode.com/2021/day/6
fun main() {
    var timers = LanternFish.timers

    for (i in 1..256) {
        timers = timers.mapValues { entry ->
            val timer = entry.key
            val previousGeneration = timers[(timer + 1) % (LanternFish.maxTimer + 1)] ?: 0
            if (timer == LanternFish.adultCycleStartTimer) (timers[0] ?: 0) + previousGeneration
            else previousGeneration
        }

        if (i == 80) println("Part 1: ${timers.values.sum()}")
        if (i == 256) println("Part 2: ${timers.values.sum()}")
    }
}

object LanternFish {
    const val maxTimer = 8
    const val adultCycleStartTimer = 6
    val timers: Map<Int, Long> = LanternFish::class.java.getResource("timers.txt")!!
        .readText()
        .split(',')
        .asSequence()
        .map { Pair(it.toInt(), 1) }
        .plus((0..maxTimer).map { Pair(it, 0) })
        .groupingBy { it.first }
        .aggregate { _, acc, elt, _ -> (acc ?: 0) + elt.second }
}