package unknownSource

import java.util.*

fun main() {
    println(mostFrequentPairs(5, listOf(
            setOf(2588, 8816, 1312, 0, 1313),
            setOf(1313, 8817, 3824, 5112, 11317, 6373, 1, 2, 11318, 8816, 10095, 10096),
            setOf(8816, 3, 7589, 11319, 5113, 1313),
            setOf(8816, 11320, 5113, 1313, 11321, 10097, 11317, 10096),
            setOf(8816, 7590, 5113, 8818, 11317, 1313, 10098, 1314, 4, 6374, 10096),
            setOf(8816, 3, 7589, 11319, 1315, 1313, 7591, 10096, 7592),
            setOf(8816, 2589, 1315, 10097, 10096, 10099, 5, 1313, 5114),
            setOf(8816, 7590, 1315, 0, 1314, 4, 7593, 1313, 8818),
    ), 3)) // [[11317, 8816], [11317, 10096], [1313, 10096], [8816, 1313], [8816, 10096]]
}

fun <T> mostFrequentPairs(top: Int, sets: List<Set<T>>, threshold: Int): List<Set<T>> {
    val frequentItems = getFrequentItems(sets, threshold)
    val counters = countPairOccurrences(sets, frequentItems)
    return selectTop(counters, top, threshold)
}

private fun <T> getFrequentItems(sets: List<Set<T>>, threshold: Int) =
        sets.asSequence()
            .flatMap { it.asSequence() }
            .groupingBy { it }
            .eachCount().asSequence()
            .flatMap { if (it.value >= threshold) sequenceOf(it.key) else emptySequence() }
            .toSet()

private fun <T> countPairOccurrences(sets: List<Set<T>>, frequentItems: Set<T>): Map<Set<T>, Int> {
    val counters = mutableMapOf<Set<T>, Int>()
    for (set in sets) {
        for (left in set) {
            if (left !in frequentItems) continue
            for (right in set) {
                // can use reference equality because the set can contain no duplicate.
                // Will save some time for complex objects.
                if (left == right || right !in frequentItems) continue
                counters.merge(setOf(left, right), 1) { a, b -> a + b }
            }
        }
    }
    return counters
}

private fun <T> selectTop(counters: Map<Set<T>, Int>, top: Int, threshold: Int): List<Set<T>> {
    val occurrenceComparator = Comparator.comparing<Set<T>, Int> { counters[it]!! }
    val selected = PriorityQueue(occurrenceComparator)

    for (entry in counters.entries) {
        if (entry.value >= threshold) {
            selected +=
                    if (selected.size < top) entry.key
                    else maxOf(selected.remove(), entry.key, occurrenceComparator)
        }
    }

    return selected.toList()
}
