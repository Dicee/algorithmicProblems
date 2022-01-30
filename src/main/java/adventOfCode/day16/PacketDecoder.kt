package adventOfCode.day16

// Difficulty: medium, honestly it took me a while to get the code exactly correct, due to mishandling the last few bits to ignore. I had a few conditions
//             in different places that made it work, but I wanted to get rid of them as they felt hacky. I did make my life harder by refusing to make copies
//             of the string or parts of it, and instead use the original string and maintain the correct integer range to look at within it. Then, I took even
//             more time to refactor my code to make it shorter and more readable.
//
//             The thing is that it's tough to debug this type of binary decoding because there could be an off-by-one problem at any point, which would cause
//             the binary string to be interpreted completely differently, but it could only cause a failure much later, or simply an incorrect result, which
//             is even harder to debug. Still fun to do, I didn't have to do this type of things very often...

// https://adventofcode.com/2021/day/16
fun main() {
    val decoded = PacketDecoder.decodeOperatorPacket(PacketDecoder.binaryPackets).first
    println("Part 1: ${calculateVersionSum(decoded)}")
    println("Part 2: ${evaluate(decoded)}")
}

private fun calculateVersionSum(packet: Packet): Int = packet.version + when (packet) {
    is LiteralPacket -> 0
    is OperatorPacket -> packet.subPackets.sumOf(::calculateVersionSum)
}

private fun evaluate(packet: Packet): Long = when (packet) {
    is LiteralPacket -> packet.value
    is OperatorPacket -> when (packet.type) {
        0 -> packet.subPackets.sumOf(::evaluate)
        1 -> packet.subPackets.fold(1L) { acc, p -> acc * evaluate(p) }
        2 -> packet.subPackets.minOf(::evaluate)
        3 -> packet.subPackets.maxOf(::evaluate)
        5 -> if (evaluate(packet.subPackets[0]) > evaluate(packet.subPackets[1])) 1 else 0
        6 -> if (evaluate(packet.subPackets[0]) < evaluate(packet.subPackets[1])) 1 else 0
        7 -> if (evaluate(packet.subPackets[0]) == evaluate(packet.subPackets[1])) 1 else 0
        else -> throw IllegalArgumentException("Invalid packet type ${packet.type}")
    }
}

sealed interface Packet {
    val version: Int
    val type: Int
}

data class OperatorPacket(override val version: Int, override val type: Int, val subPackets: List<Packet>) : Packet
data class LiteralPacket(override val version: Int, val value: Long) : Packet {
    override val type = PacketDecoder.literalType
}

object PacketDecoder {
    const val literalType = 4
    val binaryPackets = hexToBinary(PacketDecoder::class.java.getResource("hex.txt")!!.readText())

    fun decodeOperatorPacket(binaryString: String, range: IntRange = binaryString.indices): Pair<OperatorPacket, Int> {
        val type = toInt(binaryString, range.first + 3 until range.first + 6)
        val version = toInt(binaryString, range.first until range.first + 3)
        val lengthType = binaryString[range.first + 6]
        val lengthTypeStart = range.first + 7

        val (subPackets, nextPacketIndex) = if (lengthType == '0') {
            val lengthTypeEnd = lengthTypeStart + 15
            val subPacketsLength = toInt(binaryString, lengthTypeStart until lengthTypeEnd)
            decodeSubPackets(binaryString, lengthTypeEnd until lengthTypeEnd + subPacketsLength)
        } else {
            val lengthTypeEnd = lengthTypeStart + 11
            val subPacketsCount = toInt(binaryString, lengthTypeStart until lengthTypeEnd)
            decodeSubPackets(binaryString, lengthTypeEnd..range.last, remaining = subPacketsCount)
        }

        return OperatorPacket(version, type, subPackets) to nextPacketIndex
    }

    private tailrec fun decodeSubPackets(
            binaryString: String,
            range: IntRange,
            remaining: Int = Int.MAX_VALUE,
            acc: MutableList<Packet> = mutableListOf()
    ): Pair<List<Packet>, Int> {
        if (remaining == 0 || range.isEmpty()) return acc to range.first

        val type = toInt(binaryString, range.first + 3 until range.first + 6)
        val (packet, nextPacketIndex) =
                if (type == literalType) decodeLiteralPacket(binaryString, range.first)
                else decodeOperatorPacket(binaryString, range)

        acc.add(packet)
        return decodeSubPackets(binaryString, nextPacketIndex..range.last, remaining - 1, acc)
    }

    private fun decodeLiteralPacket(binaryString: String, start: Int): Pair<LiteralPacket, Int> {
        val version = toInt(binaryString, start until start + 3)

        val groupSize = 5
        var nextPacketIndex: Int = -1

        val value = buildString {
            var i = start + 6
            while (nextPacketIndex < 0) {
                append(binaryString, i + 1, i + groupSize)
                if (binaryString[i] == '0') nextPacketIndex = i + groupSize
                i += groupSize
            }
        }.toLong(2)

        return LiteralPacket(version, value) to nextPacketIndex
    }

    private fun hexToBinary(hex: String) = hex.asSequence().map {
        val digit = Integer.parseInt(it.toString(), 16)
        String.format("%4s", Integer.toBinaryString(digit)).replace(' ', '0')
    }.joinToString("")

    private fun toInt(binaryString: String, range: IntRange) = Integer.parseInt(binaryString, range.first, range.last + 1, 2)
}