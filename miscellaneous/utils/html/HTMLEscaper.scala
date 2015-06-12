package miscellaneous.utils.html

object HTMLEscaper {
	private val SPECIAL_CHARACTERS_TO_UNESCAPED = Map("&nbsp;" -> " ", "&#039;" -> "'", "&quot;" -> "\"")
	private val SPECIAL_CHARACTERS_TO_ESCAPED   = SPECIAL_CHARACTERS_TO_UNESCAPED.map { case (k,v) => (v,k) }.toMap
	
	def unescape(s: String) = {
		var str = s
		SPECIAL_CHARACTERS_TO_UNESCAPED.foreach { case (k,v) => str = str.replace(k,v) }
		str
	}
}