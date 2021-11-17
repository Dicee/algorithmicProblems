package hackerrank.dataStructures.trie.noPrefixSet

import scala.collection.mutable
import scala.collection.mutable.HashMap

// Difficulty: moderate, harder than I thought at first. I missed a few edge cases and took longer than I would have liked
//             to handle them.

// https://www.hackerrank.com/challenges/no-prefix-set
object Solution {
    // still weird to my inner Java programmer, but Scala has the same case convention for classes and constants
    private val CharsRange = 'j' - 'a' + 1

    private class NoPrefixTrie {
        private val children = new mutable.HashMap[Char, NoPrefixTrie](CharsRange / 4, mutable.HashMap.defaultLoadFactor)

        def this(chars: List[Char]) = { this(); this += chars }

        // returns true if and only if at least one new node has been inserted.
        // When returning false, the element won't be inserted
        def +=(chars: List[Char]): Boolean = chars match {
            case Nil    => false
            case h :: Nil => children.put(h, new NoPrefixTrie).isEmpty
            case h :: t => children.get(h) match {
                case None        => children += h -> new NoPrefixTrie(t); true
                case Some(child) => child.children.nonEmpty && (child += t)
            }
        }
    }

    def main(args: Array[String]) {
        val noPrefixTree = new NoPrefixTrie
        scala.io.Source.stdin.getLines.drop(1).find(word => !(noPrefixTree += word.toList)) match {
            case None       => println("GOOD SET")
            case Some(word) => println(s"BAD SET\n$word")
        }
    }
}
