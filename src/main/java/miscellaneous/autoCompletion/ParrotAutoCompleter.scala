package miscellaneous.autoCompletion

import com.dici.collection.mutable.WeightedTrie

/**
  * This auto-completer learns from the user's input, and therefore does not require a preliminary dictionary.
  */
class ParrotAutoCompleter extends AutoCompleter {
  private val trie = new WeightedTrie[Char, String]()

  override def topSuggestions(prefix: String): Iterator[String] = trie.prefixedBy(prefix)
  override def update(sentence: String*): Unit = sentence.foreach(trie += _)
}
