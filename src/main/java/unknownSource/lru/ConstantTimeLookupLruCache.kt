package unknownSource.lru

// optimal implementation from a time complexity perspective
class ConstantTimeLookupLruCache<K, V>(private val maxSize: Int) : LruCache<K, V> {
    init {
        if (maxSize <= 0) throw IllegalArgumentException("maxSize must be positive but was $maxSize")
    }

    private val map = mutableMapOf<K, Node<K, V>>()
    private val nodes = DoublyLinkedList<K, V>()

    override fun get(key: K): V? {
        val node = map[key]
        if (node != null) nodes.moveToTail(node)
        return node?.value
    }

    override fun put(key: K, value: V) {
        if (map.size == maxSize) map.remove(nodes.pop().key)

        val node = nodes.append(key, value)
        map[key] = node
    }

    override fun toMap(): Map<K, V> {
        return map.mapValues { it.value.value }
    }

}

private class DoublyLinkedList<K, V> {
    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null

    fun pop(): Node<K, V> {
        if (head == null) throw NoSuchElementException("Cannot pop on an empty list")

        val node = head
        head = head!!.next

        if (head == null) tail = null
        else head!!.previous = null

        return node!!
    }

    fun append(key: K, value: V): Node<K, V> {
        val node = Node(key, value)
        return appendNode(node)
    }

    fun moveToTail(node: Node<K, V>) {
        if (node.next == null) return

        node.next!!.previous = node.previous

        if (node.previous != null) node.previous!!.next = node.next
        else pop()

        appendNode(node)
    }

    private fun appendNode(node: Node<K, V>): Node<K, V> {
        node.previous = null
        node.next = null

        if (head == null) {
            head = node
            tail = node
        } else {
            node.previous = tail
            tail!!.next = node
            tail = node
        }
        return node
    }
}

private data class Node<K, V>(
    val key: K,
    val value: V,
    var previous: Node<K, V>? = null,
    var next: Node<K, V>? = null,
)
