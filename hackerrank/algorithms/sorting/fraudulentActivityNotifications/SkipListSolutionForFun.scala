package hackerrank.algorithms.sorting.fraudulentActivityNotifications

import java.util

import com.google.common.annotations.VisibleForTesting

import scala.collection.mutable.ListBuffer
import scala.util.Random

// Notes: before even starting to implement my first idea, I knew this problem had to have a simpler solution using plain
//        sorts rather than implementing a rather advanced version of a skip list (required to be growable, shrinkable,
//        indexable and support duplicates) but I had never implemented one (only knew the principle in the abstract) and
//        I thought it was interesting. Originally my solution was too slow (see previous commit), but after an optimization
//        allowing duplicates without adding any additional node, it became fast enough for all tests. I really enjoyed
//        implementing this skip list and having to find solutions to optimize the algorithm on my own to accommodate for
//        the probably peculiar set of properties it needed to have.
//
//        Overall, while this solution is a lot more complicated than using bucket sorting, I believe it is more general-purpose
//        and would scale well to a version of this problem where elements belong to a wide range of values, while bucket
//        sort would become unpractical/impossible.

// https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem
object SkipListSolutionForFun {
  def activityNotifications(expenditure: Array[Int], window: Int): Int = {
    val skipList = new SkipList()
    skipList ++= expenditure.view.take(window)

    var notifications = 0
    val halfWindow = window / 2

    for (i <- (window until expenditure.length).view) {
      val (spentFirstDay, spentToday) = (expenditure(i - window), expenditure(i))
      val median = if (window % 2 == 1) skipList(halfWindow) else (skipList(halfWindow - 1) + skipList(halfWindow)).toDouble / 2
      if (spentToday >= 2 * median) notifications += 1

      skipList -= spentFirstDay
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

      val buffer = new ListBuffer[Int]

      var duplicates = node.distanceToRight
      node = node.right

      while (node != null) {
        for (_ <- 1 to duplicates) buffer += node.value
        duplicates = node.distanceToRight
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

          // this will happen because we insert a single node to represent many duplicate values, and we encode this
          // information in the distance. Therefore, we won't always
          if (remaining > 0) {
            if (node.bottom == null) {
              assert(node.distanceToRight > remaining)
              node = node.right
              remaining = 0
            } else node = node.bottom
          }
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
        var isDuplicate = false

        while (!path.isEmpty) {
          val (previous, additionalDistance) = path.pop()

          // typical implementations don't need (or want) to support duplicates, but we do because they also affect the median.
          // There is no interest in creating a link between two equal nodes since it wouldn't help skipping anything.
          isDuplicate ||= currentHeight == 1 && previous.right != null && previous.right.value == toInsert

          if (currentHeight <= insertionHeight && !isDuplicate) {
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
        val path = pathTo(toRemove)
        if (path.isEmpty) return false

        var (node, _) = path.peek()
        if (node.right == null || node.right.value != toRemove) return false

        val isDuplicate = node.distanceToRight > 1

        while (!path.isEmpty) {
          node = path.pop()._1
          if (!isDuplicate && node.right != null && node.right.value == toRemove) {
            node.distanceToRight += node.right.distanceToRight - 1
            node.right = node.right.right
          } else {
            node.distanceToRight -= 1
          }
        }
        true
      }
    }
  }
}
