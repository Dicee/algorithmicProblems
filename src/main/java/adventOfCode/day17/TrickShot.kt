package adventOfCode.day17


fun main() {
    val x = X(5)
    x.x = 17
    println(x.x)
//    val decoded = TrickShot.targetArea
//    println("Part 1: ${part1()}")
//    println("Part 2: ${evaluate(decoded)}")
}

private fun part1() {

}

class X(var x: Int)  {

}

object TrickShot {
    val targetArea = TrickShot::class.java.getResource("targetArea.txt")!!.readText()
}