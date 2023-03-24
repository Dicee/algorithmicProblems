package unknownSource

import scala.collection.mutable

class LeastRecentlyUsedCache[K, V](maxSize: Int) {
  if (maxSize <= 0) throw new IllegalArgumentException(f"maxSize must be positive but was ${maxSize}")

  private[this] val map = mutable.Map[K, Node]()
  private[this] val nodes = new DoublyLinkedList()

  def +=(kv: (K, V)): Unit = {
    if (map.size == maxSize) {
      map -= nodes.pop().key
    }

    val node = map.get(kv._1).map(_.copy(value = kv._2)).getOrElse(nodes.append(kv._1, kv._2))
    map += kv._1 -> node

    markUsed(node)
  }

  def get(key: K): Option[V] = {
    val node = map.get(key)
    node.foreach(markUsed)
    node.map(_.value)
  }

  private def markUsed(node: Node) {
    nodes.moveToTail(node)
  }

  def toMap: Map[K, V] = map.view.mapValues(_.value).toMap
  override def toString = toMap.toString

  class DoublyLinkedList {
    private var head: Node = _
    private var tail: Node = _

    def pop(): Node = {
      if (head == null) throw new NoSuchElementException("Cannot pop on an empty list")

      val node = head
      head = head.next

      if (head == null) tail = null
      else head.previous = null

      node
    }

    def append(key: K, value: V): Node = {
      val node = new Node(key, value)
      appendNode(node)
    }

    def moveToTail(node: Node): Unit = {
      if (node.next == null) return

      node.next.previous = node.previous

      if (node.previous != null) node.previous.next = node.next
      else pop()

      appendNode(node)
    }

    private def appendNode(node: Node) = {
      node.previous = null
      node.next = null

      if (head == null) {
        head = node
        tail = node
      } else {
        node.previous = tail
        tail.next = node
        tail = node
      }
      node
    }
  }

  case class Node(key: K, value: V, var previous: Node = null, var next: Node = null)
}

object Main {
  def main(args: Array[String]): Unit = {
    val cache = new LeastRecentlyUsedCache[Int, Int](3)

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

    insert(3)
    insert(8)
    assertCacheContains(3, 7, 8)

    useCache(3)
    useCache(7)
    insert(9)
    assertCacheContains(3, 7, 9)

    def insert(v: Int): Unit = cache += v -> v
    def useCache(key: Int): Unit = println(cache.get(key).map(_ => f"Used key $key").getOrElse(f"Key $key was missing"))
    def assertCacheContains(values: Int*): Unit = {
      val asMap = cache.toMap
      println(asMap)
      assert(asMap == values.map(v => (v, v)).toMap)
    }
  }
}

