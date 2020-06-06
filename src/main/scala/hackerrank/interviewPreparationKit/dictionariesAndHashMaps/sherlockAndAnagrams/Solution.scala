package hackerrank.interviewPreparationKit.dictionariesAndHashMaps.sherlockAndAnagrams

import scala.collection.mutable

// Difficulty: I found the solution pretty easily but my solution is quadratic, not sure if there's better. It's senough to pass all tests at least.

// https://www.hackerrank.com/challenges/sherlock-and-anagrams/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
object Solution {
    def sherlockAndAnagrams(s: String): Int = {
        val substrings = for (i <- s.indices; l <- 1 to s.length - i) yield new Word(s.view(i, i + l))
        // number of pairs in a set of n elements: C(2, n) = n! / (2! (n - 2)!) = n (n - 1) / 2
        substrings.groupBy(identity).values.map(duplicates => duplicates.size * (duplicates.size - 1) / 2).sum
    }

    private class Word(word: Iterable[Char]) {
        private val map = mutable.HashMap[Char, Int]()
        for (ch <- word) map += ch -> (map.getOrElse(ch, 0) + 1)

        override lazy val hashCode = map.hashCode
        override def equals(that: Any) = that match {
            case w: Word => map == w.map
            case _ => false
        }
    }
}
