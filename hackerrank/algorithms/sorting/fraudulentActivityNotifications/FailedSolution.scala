package hackerrank.algorithms.sorting.fraudulentActivityNotifications

import java.util

import com.google.common.annotations.VisibleForTesting

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random

// Notes: before even starting to implement my first idea, I knew this problem had to have a simpler solution using plain
//        sorts rather than implementing a rather advanced version of a skip list (required to be growable, shrinkable,
//        indexable and support duplicates) but I had never implemented one (only knew the principle in the abstract) and
//        I thought it was interesting. For now my implementation is too slow for lists with a high number of duplicates,
//        but I have good hopes to be able to optimize it. It's interesting because it goes beyond the scope of what you
//        can typically read about skip lists, although I'm sure some people have already done it, and better than me!

// https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem
object FailedSolution {
  def activityNotifications(expenditure: Array[Int], window: Int): Int = {
    val skipList = new SkipList()
    skipList ++= expenditure.view.take(window)

    var notifications = 0
    val halfWindow = window / 2

    val dailySpend = expenditure.toList
    dailySpend.zip(dailySpend.drop(window)).foreach { case (spentYesterday, spentToday) =>
      val median = if (window % 2 == 1) skipList(halfWindow) else (skipList(halfWindow) + skipList(halfWindow + 1)) / 2
      if (spentToday >= 2 * median) notifications += 1

      skipList -= spentYesterday
      skipList += spentToday
    }

    notifications
  }

  class SkipList(insertProbability: Float = 0.25f) {
    private val rd = new Random(10)

    private var _size = 0
    private var height = 1
    private var head = new Node(Int.MinValue)

    def ++=(values: Iterable[Int]): Unit = values.foreach(this += _)

    def insert(value: Int): Unit = {
      this += value
    }

    def +=(value: Int): Unit = {
      var insertionHeight = 1
      while (rd.nextFloat() <= insertProbability) insertionHeight += 1
      while (height < insertionHeight) {
        head = new Node(Int.MinValue, bottom = head)
        height += 1
      }

      head.insert(value, insertionHeight)
      _size += 1
    }

    def remove(value: Int) = this -= value
    def -=(value: Int) = {
      val removed = head.remove(value)
      if (removed) _size -= 1
      removed
    }

    def get(index: Int) = apply(index)
    def apply(index: Int) = {
      assert(index < _size)
      head(index)
    }

    def size = _size

    @VisibleForTesting
    private[fraudulentActivityNotifications] def toList = {
      var node = head
      while (node.bottom != null) node = node.bottom
      node = node.right

      val buffer = new ListBuffer[Int]

      while (node != null) {
        buffer += node.value
        node = node.right
      }

      import scala.collection.JavaConverters
      JavaConverters.seqAsJavaList(buffer.toList)
    }

    // null is not very Scalatic but will help reducing the memory overhead of the pointers
    private class Node(val value: Int, var right: Node = null, var bottom: Node = null, var distanceToRight: Int = 0) {
      def apply(index: Int) = {
        var remaining = index + 1
        var node = this

        while (remaining > 0) {
          while (node.right != null && node.distanceToRight <=
            remaining) {
            remaining -= node.distanceToRight
            node = node.right
          }

          if (remaining > 0) node = node.bottom
        }
        node.value
      }

      def insert(toInsert: Int, insertionHeight: Int): Unit = insertAtAllLevels(pathTo(toInsert), toInsert, insertionHeight)

      private def pathTo(toInsert: Int) = {
        val path = new util.ArrayDeque[(Node, Int)]()
        var node = this

        do {
          var distance = 0
          while (node.right != null && node.right.value < toInsert) {
            distance += node.distanceToRight
            node = node.right
          }

          path.push((node, distance))
          node = node.bottom
        } while (node != null)
        path
      }

      private def insertAtAllLevels(path: util.Deque[(Node, Int)], toInsert: Int, insertionHeight: Int): Unit = {
        var inserted: Node = null
        var distance = 1
        var currentHeight = 1

        while (!path.isEmpty) {
          val (previous, additionalDistance) = path.pop()

          // typical implementations don't need (or want) to support duplicates, but we do because they also affect the median.
          // There is no interest in creating a link between two equal nodes since it wouldn't help skipping anything.
          lazy val isDifferentFromNeighbours = previous.value != toInsert && (inserted.right == null || inserted.right.value != toInsert)
          if (currentHeight <= insertionHeight && (inserted == null || isDifferentFromNeighbours)) {
            val distanceToRight = if (previous.right == null) 0 else previous.distanceToRight - distance + 1
            inserted = new Node(toInsert, previous.right, inserted, distanceToRight)
            previous.right = inserted
            previous.distanceToRight = distance
            distance += additionalDistance
          } else {
            previous.distanceToRight += 1
          }

          currentHeight += 1
        }
      }

      def remove(toRemove: Int): Boolean = {
        var node = this
        val path = mutable.ArrayStack[Node]()
        var found = false

        do {
          while (node.right != null && node.right.value < toRemove) {
            node = node.right
          }
          if (node.right != null && node.right.value == toRemove) {
            node.distanceToRight += node.right.distanceToRight - 1
            node.right = node.right.right
            found = true
          } else {
            path.push(node)
          }

          node = node.bottom
        } while (node != null)
        if (found) path.foreach(_.distanceToRight -= 1)
        found
      }
    }
  }
}
