import java.util.ArrayDeque

/**
 * Difficulty: easy, marked as medium. Forgot one edge case (folder name prefixed by dots), other than this it's very easy.
 *
 * https://leetcode.com/problems/simplify-path
 */
fun simplifyPath(path: String): String {
    val dirs = ArrayDeque<String>()

    var i = 0
    while (i < path.length) {
        val ch = path[i]
        if (ch == '/') i += 1
        else {
            var onlyDots = ch == '.'
            var nextSlashIndex = i + 1
            while (nextSlashIndex < path.length && path[nextSlashIndex] != '/') {
                onlyDots = onlyDots && path[nextSlashIndex] == '.'
                nextSlashIndex += 1
            }

            if (ch == '.' && onlyDots) {
                when (nextSlashIndex - i) {
                    1 -> {}
                    2 -> if (dirs.isNotEmpty()) dirs.removeLast()
                    else -> dirs.addLast(path.substring(i, nextSlashIndex))
                }
            } else {
                dirs.addLast(path.substring(i, nextSlashIndex))

            }

            i = nextSlashIndex
        }
    }

    return buildString {
        append('/')
        append(dirs.joinToString("/"))
    }
}

simplifyPath("/Users/courtino/scratches/") // /Users/courtino/scratches
simplifyPath("/home/") // /home
simplifyPath("/home//foo/") // /home/foo
simplifyPath("/home//foo/") // /home/foo
simplifyPath("/home/user/Documents/../Pictures") // /home/user/Documents/Pictures
simplifyPath("/../") // /
simplifyPath("/.../a/../b/c/../d/./") // /.../b/d
simplifyPath("/Users/courtino/scratches/../../") // /Users
simplifyPath("/Users/courtino/scratches/../../../") // /Users
simplifyPath("/..hidden") // /..hidden
