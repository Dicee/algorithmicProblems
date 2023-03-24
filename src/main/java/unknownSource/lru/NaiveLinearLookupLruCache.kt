package unknownSource.lru

import java.util.LinkedList

class NaiveLinearLookupLruCache<K, V>(private val maxSize: Int) : LruCache<K, V> {
    private val map: MutableMap<K, V> = mutableMapOf()
    private val orderedKeys: LinkedList<K> = LinkedList()

    init {
        if (maxSize <= 0) throw IllegalArgumentException("maxSize must be positive but was $maxSize")
    }

    override fun get(key: K): V? {
        val value = map[key]
        if (value!= null) markUsed(key)
        return value
    }

    override fun put(key: K, value: V) {
        if (map.size == maxSize && key !in map) {
            map.remove(orderedKeys.removeFirst())
        }

        markUsed(key)
        map[key] = value
    }

    // that's the expensive part in this implementation, that's why it's so naive
    private fun markUsed(key: K) {
        val index = orderedKeys.indexOf(key)
        if (index >= 0) orderedKeys.removeAt(index)
        orderedKeys.addLast(key)
    }

    override fun toMap(): Map<K, V> = map
}