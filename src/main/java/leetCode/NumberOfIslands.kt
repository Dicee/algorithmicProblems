/**
 * Difficulty: trivial, marked as medium. I immediately thought about a flood fill, and there's no particular difficulty in the implementation.
 *
 * https://leetcode.com/problems/number-of-islands/submissions
 */
fun numIslands(grid: Array<CharArray>): Int {
    val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun floodFill(grid: Array<CharArray>, coord: Pair<Int, Int>) {
        val deque = ArrayDeque<Pair<Int, Int>>()
        deque.add(coord)

        while (deque.isNotEmpty()) {
            val (i, j) = deque.removeFirst()
            grid[i][j] = 'x'

            for ((di, dj) in directions) {
                if (
                    i + di >= 0 &&
                    i + di <= grid.lastIndex &&
                    j + dj >= 0 &&
                    j + dj <= grid[0].lastIndex &&
                    grid[i + di][j + dj] == '1'
                ) {
                    grid[i + di][j + dj] = 'x'
                    deque.add(i + di to j + dj)
                }
            }
        }
    }

    var counter = 0
    for (i in grid.indices) {
        for (j in grid[0].indices) {
            if (grid[i][j] == '1') {
                counter++
                floodFill(grid, i to j)
            }
        }
    }

    return counter
}

println(numIslands(arrayOf(
    charArrayOf('1','1','1','1','0'),
    charArrayOf('1','1','0','1','0'),
    charArrayOf('1','1','0','0','0'),
    charArrayOf('0','0','0','0','0'),
))) // 1
 
println(numIslands(arrayOf(
    charArrayOf('1','1','0','0','0'),
    charArrayOf('1','1','0','0','0'),
    charArrayOf('0','0','1','0','0'),
    charArrayOf('0','0','0','1','1'),
))) // 3

println(numIslands(arrayOf(
    charArrayOf('1','1','1','1','1','0','1','1','1','1','1','1','1','1','1','0','1','0','1','1'),
    charArrayOf('0','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','0'),
    charArrayOf('1','0','1','1','1','0','0','1','1','0','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','0','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','0','1','1','1','1','1','1','0','1','1','1','0','1','1','1','0','1','1','1'),
    charArrayOf('0','1','1','1','1','1','1','1','1','1','1','1','0','1','1','0','1','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','0','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('0','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','1','0','1','1','1','1','1','1','1','0','1','1','1','1','1','1'),
    charArrayOf('1','0','1','1','1','1','1','0','1','1','1','0','1','1','1','1','0','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','0'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','0','0'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
    charArrayOf('1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'),
))) // 1
