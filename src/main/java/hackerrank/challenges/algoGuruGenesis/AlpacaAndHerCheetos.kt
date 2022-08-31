package hackerrank.challenges.algoGuruGenesis

import java.math.BigInteger
import java.util.*

/**
 * Internal Amazon contest. Test challenge (may not remain active forever): https://www.hackerrank.com/contests/test-1657703073/challenges.
 *
 * Difficulty: medium
 */
fun main() {
    var (_, cheetosToThrow) = readLine()!!.trim().split(' ').map { BigInteger(it) }
    val packets = readLine()!!.trim().split(' ').asSequence()
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()

    val queue = PriorityQueue(naturalOrder<PacketGroup>().reversed())
    for ((numCheetos, groupSize) in packets) queue.add(PacketGroup(BigInteger(numCheetos.toString()), BigInteger(groupSize.toString())))

    var jumps = BigInteger.ZERO

    while (cheetosToThrow > BigInteger.ZERO) {
        val largest = queue.poll()
        if (cheetosToThrow < largest.groupSize) {
            jumps += cheetosToThrow * largest.numCheetos
            cheetosToThrow = BigInteger.ZERO
        } else {
            val secondLargest = queue.peek() ?: PacketGroup(BigInteger.ZERO, BigInteger.ZERO)
            val maxThrows = largest.numCheetos - secondLargest.numCheetos
            val throws = minOf(cheetosToThrow / largest.groupSize, maxThrows)

            jumps += largest.groupSize * sumIntegersBetween(largest.numCheetos - throws, largest.numCheetos)

            if (throws == maxThrows) {
                queue.poll()
                queue += PacketGroup(secondLargest.numCheetos, largest.groupSize + secondLargest.groupSize)
            } else {
                queue += PacketGroup(largest.numCheetos - throws, largest.groupSize)
            }

            cheetosToThrow -= throws * largest.groupSize
        }
    }

    println(jumps % BigInteger("1000000007"))
}

private fun sumIntegersBetween(exclusiveStart: BigInteger, inclusiveEnd: BigInteger) = sumIntegersUpTo(inclusiveEnd) - sumIntegersUpTo(exclusiveStart)
private fun sumIntegersUpTo(n: BigInteger) = n * (n + BigInteger.ONE) / BigInteger("2")

data class PacketGroup(val numCheetos: BigInteger, val groupSize: BigInteger) : Comparable<PacketGroup> {
    override fun compareTo(other: PacketGroup): Int = numCheetos.compareTo(other.numCheetos)
}


