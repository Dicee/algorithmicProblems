package unknownSource.lru

import java.math.BigInteger
import java.util.NavigableMap
import java.util.TreeMap

// a downside of this implementation, besides its extra complexity, is that the timestamps will become
// bigger and bigger over time, taking more memory. It's possible to have a condition that will cause a
// "recompaction" above some threshold in order to reassign all the values in the cache  to smaller and
// contiguous timestamps
class LogarithmicLookupLruCache<K, V>(private val maxSize: Int): LruCache<K, V> {
    private val timestamps: MutableMap<K, BigInteger> = mutableMapOf()
    private val valuesByTimestamp: NavigableMap<BigInteger, Pair<K, V>> = TreeMap()
    private var currentTimestamp: BigInteger = BigInteger.ZERO

    init {
        if (maxSize <= 0) throw IllegalArgumentException("maxSize must be positive but was $maxSize")
    }

    override fun get(key: K): V? {
        val timestamp = timestamps[key]
        val value = if (timestamp != null) valuesByTimestamp[timestamp]?.second else null

        if (value != null) setAndMarkUsed(key, value)
        return value
    }

    override fun put(key: K, value: V) {
        if (timestamps.size == maxSize && key !in timestamps) {
            val (droppedKey, _) = valuesByTimestamp.remove(valuesByTimestamp.firstKey())!!
            timestamps.remove(droppedKey)
        }

        setAndMarkUsed(key, value)
    }

    private fun setAndMarkUsed(key: K, value: V) {
        currentTimestamp += BigInteger.ONE
        val timestamp = currentTimestamp

        val existingTimestamp = timestamps[key]
        if (existingTimestamp != null) valuesByTimestamp.remove(existingTimestamp)

        timestamps[key] = timestamp
        valuesByTimestamp[timestamp] = Pair(key, value)
    }

    override fun toMap(): Map<K, V> {
        return timestamps.mapValues { e -> valuesByTimestamp[e.value]!!.second }.toMap()
    }
}
