package hackerrank.interviewPreparationKit.dictionariesAndHashMaps.frequencyQueries

import scala.collection.mutable

// Difficulty: easy, not much to comment on.

// https://www.hackerrank.com/challenges/frequency-queries/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
object Solution {
    def freqQuery(queries: Array[Array[Int]]): Array[Int] = {
        val valuesCounter      = new Counter
        val occurrencesCounter = new Counter
        val buffer             = mutable.ArrayBuffer[Int]()

        for (query <- queries) {
          query match {
            case Array(1, n)    => { val x = valuesCounter.incrementAndGet(n); occurrencesCounter.incrementAndGet(x); occurrencesCounter.getAndDecrement(x - 1); }
            case Array(2, n)    => valuesCounter.getAndDecrement(n).foreach(x => { occurrencesCounter.getAndDecrement(x); if (x > 1) occurrencesCounter.incrementAndGet(x - 1) })
            case Array(3, freq) => buffer += (if (occurrencesCounter.contains(freq)) 1 else 0)
          }
        }

        buffer.toArray
    }

    private class Counter {
        private val counters = mutable.HashMap[Int, Int]()

        def contains(n: Int) = counters.get(n).isDefined

        def incrementAndGet(n: Int): Int = {
            val counter = counters.getOrElse(n, 0) + 1
            counters += n -> counter
            counter
        }

        def getAndDecrement(n: Int): Option[Int] = {
            val counter = counters.get(n)
            counter.foreach(value => if (value == 1) counters -= n else counters += n -> (value - 1))
            counter
        }
    }
}
