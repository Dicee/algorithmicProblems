package miscellaneous.autoCompletion

trait AutoCompleter {
  def topSuggestions(prefix: String): Iterator[String]
  def update(sentence: String*): Unit

  final def topSuggestions(prefix: String, n: Int): List[String] = topSuggestions(prefix).take(n).toList
}
