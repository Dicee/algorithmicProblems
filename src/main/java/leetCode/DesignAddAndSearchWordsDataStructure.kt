/**
 * Difficulty: easy, marked as medium. I immediately thought about a prefix tree, and the recursion isn't difficult to implement.
 * 
 * https://leetcode.com/problems/design-add-and-search-words-data-structure
 */
class WordDictionary {
    private val root = Node(false)

    fun addWord(word: String) {
        var node = root
        for (ch in word) node = node.children.computeIfAbsent(ch) { Node(false) }
        node.isWord = true
    }

    fun search(word: String): Boolean {
        return root.search(word, 0)
    }

    private data class Node(var isWord: Boolean, val children: MutableMap<Char, Node> = mutableMapOf()) {
        fun search(expr: String, i: Int): Boolean {
            if (expr[i] == '.') {
                if (i == expr.lastIndex) return children.values.any { it.isWord }
                return children.values.any { it.search(expr, i + 1) }
            }

            val child = children[expr[i]]
            if (child != null) {
                if (i == expr.lastIndex) return child.isWord
                return child.search(expr, i + 1)
            }
            return false
        }
    }
}


val dict1 = WordDictionary()
dict1.addWord("bad")
dict1.addWord("dad")
dict1.addWord("mad")
println(dict1.search("pad")) // false
println(dict1.search("bad")) // true
println(dict1.search(".ad")) // true
println(dict1.search("b..")) // true

val dict2 = WordDictionary()
dict2.addWord("at")
dict2.addWord("and")
dict2.addWord("an")
dict2.addWord("add")
println(dict2.search("a")) // false
println(dict2.search(".at")) // false
dict2.addWord("bat")
println(dict2.search(".at")) // true
println(dict2.search("an.")) // true
println(dict2.search("a.d.")) // false
println(dict2.search("b.")) // false
println(dict2.search("a.d")) // true
println(dict2.search(".")) // false
