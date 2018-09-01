package hackerrank.interviewPreparationKit.search.minimumTimeRequired

import scala.annotation.tailrec

// Difficulty: not hard in the end, but I struggled. Honestly, I didn't think about bissecting on the goal. I tried an implementation based
//             on a priority queue which had O(goal * log n + n.log n) complexity, which isn't bad but isn't as good as O(n.log goal) in this
//             problem since 'goal' can be much larger than n (number of machines).

// https://www.hackerrank.com/challenges/minimum-time-required
object Solution {
    def minTime(machines: Array[Long], goal: Long): Long = {
        @tailrec
        def recSol(lower: Long, upper: Long): Long = {
            if (lower == upper) lower
            else {
                val mid = (lower + upper) / 2
                val produced = machines.view.map(mid / _).sum    
                if (produced < goal) recSol(mid + 1, upper) else recSol(lower, mid)
            }
        }

        val (slowestRate, fastestRate) = (machines.length / machines.min.toDouble, machines.length / machines.max.toDouble)
        recSol((goal / slowestRate).toLong, (goal / fastestRate).toLong)
    }
}
