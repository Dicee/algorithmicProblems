import java.io.File
import java.math.BigDecimal
import java.util.function.Function
import java.util.ArrayList as JavaArrayList
import kotlin.collections.ArrayList


private fun hexToBinary(hex: String) = hex
    .flatMap { ch -> toBinaryString(if (ch.isDigit()) ch.digitToInt() else ch - 'A' + 10) }
    .joinToString("")

private fun toBinaryString(hexDigit: Int): Iterable<Char> {
    if (hexDigit > 15) throw IllegalArgumentException("Expected hexadecimal digit but was: $hexDigit")

    var mask = 0b1000
    return buildString {
        while (mask > 0) {
            append(if (hexDigit and mask > 0) 1 else 0)
            mask = mask shr 1
        }
    }.asIterable()
}

private fun toInt(binaryString: String) = toInt(binaryString, binaryString.indices)
private fun toInt(binaryString: String, range: IntRange): Int {
    var p = 1 shl (range.length() - 1)
    return binaryString.asSequence().drop(range.first).take(range.length()).fold(0) { acc, ch ->
        val n = ch.digitToInt() * p
        p = p shr 1 // not great to make side effects in a function, but I didn't want to write a loop, and pure functional approaches were longer
        acc + n
    }
}

fun IntRange.length() = endInclusive - start + 1

fun main() {
    // Downsides
    // - Lombok: https://kotlinlang.org/docs/lombok.html


    // Questions:
    // - what's the scope of extension functions? https://kotlinlang.org/docs/idioms.html#extension-functions
    // - how do we handle exceptions on try-with-resources?

    // Advanced:
    // - read all https://kotlinlang.org/docs/properties.html#delegated-properties
    // - finish https://play.kotlinlang.org/koans/Properties/Lazy%20property/Task.kt

//    calcTaxes()

//    val stream = Files.newInputStream(Paths.get("/some/file.txt"))
//    stream.buffered().reader().use { reader ->
//        println(reader.readText())
//    }

    var function = Function<Int, String> { i -> i.toString() }

    HashMap<Int, Int>()
    var timeInMillis: Long? = null
    print(timeInMillis!!)

    println("""
|hello
|  fucking
| world
        """)
    listOf<Int>().joinToString()
    listOf(1,)

    println(Customer("a", "b").copy(name = "c"))

    "x".also { println(it) }
    println("x".run { uppercase() }.map { 10 })



    val coucou = 10
    println("Hello world ${coucou * 10}")

    Test()
    SubTest()

    var x = JavaArrayList<String>()
    x = ArrayList()

    println(describe(6))
    println(describe("Hello"))

    val r = 10 downTo 1 step 2
    println(r)

    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    fruits
        .asSequence()
        .filter { it.startsWith("a") }
        .sorted()
        .map { it.uppercase() }
        .forEach { println(it) }

    val z = {
        val u = 5
        u * 8
    }

    println(z())

    println(nullable(true)?.uppercase())

    // try to fix
//    println(Testouille.builder()
//        .name("namo")
//        .value(17)
//        .build()
//    )

    Testibat(
            value = 9,
            name = "bla",
    )

    println(File(".").listFiles()?.size ?: 0)

    val first = MyDate(1, 2, 3)
    val last = MyDate(2, 2, 3)
    println(first..last)


    DateRange.print()
}

fun <T, C : MutableCollection<T>> Collection<T>.partitionTo(matches: C, rest: C, predicate: (T) -> Boolean): Pair<C, C> {
        for (elt in this) {
            (if (predicate(elt)) matches else rest).add(elt)
        }
    return Pair(matches, rest)
}

class DateRange(val start: MyDate, val end: MyDate): Iterable<MyDate> {
    companion object {
        fun print() {

        }
    }

    override operator fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current: MyDate = start

            override fun next(): MyDate {
                if (!hasNext()) throw NoSuchElementException()
                val result = current
                current = current.followingDate()
                return result
            }

            override fun hasNext(): Boolean = current <= end
        }
    }
}

fun Shop.getCustomersSortedByOrders() = customers
        .asSequence()
        .map { Pair(it, it.orders.size) }
        .sortedBy { it.second }
        .map { it.first }
        .toList()

data class Shop(val name: String, val customers: List<Customerz>)

data class Customerz(val name: String, val city: City, val orders: List<Order>)

data class Order(val products: List<Product>, val isDelivered: Boolean)

data class Product(val name: String, val price: Double)

data class City(val name: String) {
    override fun toString() = name
}

//fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
//    for (date in firstDate..secondDate) {
//        handler(date)
//    }
//}

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override operator fun compareTo(other: MyDate): Int {
        if (year < other.year) return -1
        if (year > other.year) return 1

        if (month < other.month) return -1
        if (month > other.month) return 1

        if (dayOfMonth < other.dayOfMonth) return -1
        return if (dayOfMonth == other.dayOfMonth) 0 else 1
    }

    fun followingDate() = this
}

fun calcTaxes(): BigDecimal = TODO("UV-Ix-239 - Need to implement deletions first")

fun test() {
    val result: Unit = try {
        println("yeahhh")
    } catch (e: ArithmeticException) {
        throw IllegalStateException(e)
    }

    result.also {  }
}

data class Customer(val name: String, val email: String)


class Testibat(val name: String, val value: Int)

fun nullable(isNull: Boolean): String? = if (isNull) null else "notNull"

fun describe(obj: Any): String = when (obj) {
    1, 2, 6 -> "One"
    "Hello" -> "Greeting"
    is Long -> "Long"
    !is String -> "Not a string"
    else -> "Unknown"
}

fun x(): Nothing = throw IllegalArgumentException("hello")

/**
 * @deprecated
 * @constructor
 */
/* coucou */
open class Test {
    init {
        println("Instantiated ${javaClass.name}")
    }

    private val x = 10
    val y = 7
    protected val z = 9
}

class SubTest : Test()