package unknownSource

import scala.collection.mutable

// Difficulty: medium. I came up with the main idea relatively quickly but it does require some thought about edge cases
//             and complexity. I'm still not sure my solution covers all cases and not entirely sure about my complexity.
//             It seems linear to me, with a worst case of 2*|longest| iterations (if the string contains as many
//             non-overlapping partial matches - as long as possible - that can possibly fit in the length)

object SubstringPermutations {
  def main(args: Array[String]): Unit = {
    // no permutation, no match overlap, no partial match, one full match
    println(isSubstringPermutable("garbage Deependra blabla", "Deependra")) // true

    // no permutation, no match overlap, two partial matches, one full match
    println(isSubstringPermutable("ependra eependra Deependra", "Deependra")) // true

    // permuted, no match overlap, three partial matches, one full match
    println(isSubstringPermutable("peenrda eepD_ednra edpDenera", "Deependra")) // true

    // permuted, overlapping matches, two partial matches, one full match
    println(isSubstringPermutable("peenrda eepD_eeaeepdrDn", "Deependra")) // true

    // permuted, overlapping matches, two partial matches, one full match in the middle of the string
    println(isSubstringPermutable("peenrda eepD_eeaeepdrDn hello", "Deependra")) // true

    // permuted, no overlapping match, 4 partial matches, no full match
    println(isSubstringPermutable("peenrda eepD_ednra edpDener", "Deependra")) // false

    // permuted, overlapping matches, 3 partial matches, no full match
    println(isSubstringPermutable("peenrda eepD_eeaeepdDn", "Deependra")) // false
  }

  /**
    * Is there a permutation of <code>shortest</code> that is a contiguous substring of
    * <code>longest</code>?
    * @param longest a string guaranteed to be longer than <code>shortest</code>
    * @param shortest a string guaranteed to be shorter than <code>longest</code>
    */
  def isSubstringPermutable(longest: String, shortest: String): Boolean = {
    val chars = new Multiset(shortest)
    var (matched, i) = (0, 0)

    while (i < longest.length && matched < shortest.length) {
      val ch = longest(i)
      if (chars.count(ch) > 0) {
        chars   -= ch
        matched += 1
        i       += 1
      } else if (matched > 0) {
        chars   += longest(i - matched)
        matched -= 1
      } else i  += 1
    }
    matched == shortest.length
  }

  private class Multiset[T] {
    private val map = mutable.HashMap[T, Int]()

    def this(iterable: Iterable[T]) = { this(); iterable.foreach(this += _) }

    def +=(t: T): Unit = map += t -> (count(t) + 1)
    def -=(t: T): Unit = {
      val cnt = count(t)
      assert(cnt > 0, s"Cannot remove $t as current count is zero.")
      if (cnt == 1) map -= t else map += t -> (cnt - 1)
    }
    def count(t: T) = map.getOrElse(t, 0)
  }
}
