import scala.io.Source

// Difficulty: trivial. I did not implement it as two stacks as suggested by the problem (for training purposes) because it's
//             terribly inefficient.

// https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks
object Solution {
    def main(args: Array[String]) {
        val queue = new IntQueue
        Source.stdin.getLines.drop(1).map(_.split(' ').map(_.toInt)).foreach { 
            case Array(1, value) => queue.enqueue(value)
            case Array(2)        => queue.dequeue()
            case Array(3)        => println(queue.peek())
        }
    }
    
    private class IntQueue {
        private var head: Node = null
        private var tail: Node = null

        def peek() = {
            checkNonEmpty(head)
            head.value
        }

        def enqueue(value: Int) = {
            val node = new Node(value, null)

            if (head == null) {
                head = node
                tail = node
            } else {
                tail.next = node
                tail      = node
            } 
        }

        def dequeue() = {
            checkNonEmpty(head)

            val value = head.value
            head = head.next
            if (head == null) tail = null
        }

        private def checkNonEmpty(node: Node) = if (node == null) throw new NoSuchElementException()
        private class Node(val value: Int, var next: Node)
    }
}
