package hackerrank.interviewPreparationKit.stackAndQueues.largestRectangle

import scala.collection.mutable

// Difficulty: medium. It made me think a bit, but eventually quite easy.

// https://www.hackerrank.com/challenges/largest-rectangle/problem?h_l=interview&playlist_slugs[][]=interview-preparation-kit&playlist_slugs[][]=stacks-queues
object Solution {
    def largestRectangle(heights: Array[Int]): Long = {
        def area(building: Building, currentIndex: Int) = (currentIndex - building.index) * building.height
        
        val stack = new mutable.ArrayStack[Building]
        var max   = 0L

        for (i <- 0 until heights.length) {
            val h     = heights(i)
            var index = i

            while (stack.nonEmpty && h < stack.head.height) {
                val building = stack.pop()
                max   = Math.max(max, area(building, i))
                index = building.index
            }

            if (stack.isEmpty || h > stack.head.height) stack.push(Building(h, index))
        }
        
        while (stack.nonEmpty) max = Math.max(max, area(stack.pop(), heights.length))                                        
        max
    }

    private case class Building(height: Int, index: Int)
}
