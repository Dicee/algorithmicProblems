package unknownSource.lru

// no expectation of thread-safety
interface LruCache<K, V> {
    operator fun get(key: K): V?
    fun put(key: K, value: V)
    fun toMap(): Map<K, V>
}

fun main() {
//    val cache: LruCache<Int, Int> = NaiveLinearLookupLruCache(3)
//    val cache: LruCache<Int, Int> = LogarithmicLookupLruCache(3)
    val cache: LruCache<Int, Int> = ConstantTimeLookupLruCache(3)

    fun insert(v: Int) = cache.put(v, v)
    fun useCache(key: Int) {
        val value = cache[key]
        println(if (value == null) "Key $key was missing" else "Used key $key")
    }
    fun assertCacheContains(vararg values: Int) {
        val asMap = cache.toMap()
        println(asMap)
        assert(asMap == values.associateWith { v -> v })
    }

    assertCacheContains()
    insert(1)
    assertCacheContains(1)
    insert(2)
    assertCacheContains(1, 2)
    insert(3)
    assertCacheContains(1, 2, 3)
    insert(4)
    assertCacheContains(2, 3, 4)
    insert(5)
    assertCacheContains(3, 4, 5)

    useCache(17)
    useCache(3)
    insert(6)
    assertCacheContains(3, 5, 6)

    insert(7)
    assertCacheContains(3, 6, 7)

    useCache(3)
    insert(8)
    assertCacheContains(3, 7, 8)

    useCache(3)
    useCache(7)
    insert(9)
    assertCacheContains(3, 7, 9)
}


