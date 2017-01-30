package miscellaneous.autoCompletion

/**
  * This auto-completer will try returning some suggestions when the prefix did not match any known word by dropping the
  * last character until it finds a suggestion
  */
trait TolerantAutoCompleter extends AutoCompleter {
  def MaxRetries: Int

  abstract override def topSuggestions(prefix: String): Iterator[String] = {
    if (MaxRetries < 1) throw new IllegalArgumentException("Should allow 1 retry minimum, but max retries was: " + MaxRetries)
    for (attempt <- 0 to Math.min(prefix.length, MaxRetries)) {
      val suggestions = super.topSuggestions(prefix.substring(0, prefix.length - attempt))
      if (suggestions.hasNext) return suggestions
    }
    Iterator()
  }
}
