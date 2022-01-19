package adventOfCode.day4

// Difficulty: easy but annoying, quite a lot of code!

// https://adventofcode.com/2021/day/4
fun main() {
    val bingo = GiantSquidPartOne.bingo()

    // given the scale of the problem, I'll just brute-force it, no need to be smart about it
    val playableBoards: List<PlayableBoard> = List(bingo.boards.size) {
        val cells = bingo.boards[0].size
        MutableList(cells) { MutableList(cells){ false } }
    }

    val outcome = determineLastWinningBoard(bingo, playableBoards)
    println(calculateScore(outcome))
}

fun determineBingoOutcome(bingo: Bingo, playableBoards: List<PlayableBoard>): BingoOutcome {
    val draws = bingo.draws.iterator()

    // assuming there's always a winner
    while (true) {
        val draw = draws.next()

        for ((boardIndex, board) in bingo.boards.withIndex()) {
            for (i in board.indices) {
                for (j in board[0].indices) {
                    if (board[i][j] == draw) {
                        val playableBoard = playableBoards[boardIndex]
                        playableBoard[i][j] = true

                        // assuming there's only one winner
                        if (hasCompleteRowOrColumn(playableBoard, i, j)) {
                            return BingoOutcome(board, playableBoard, draw)
                        }
                    }
                }
            }
        }
    }
}

fun hasCompleteRowOrColumn(board: PlayableBoard, row: Int, column: Int): Boolean {
    if (board[row].all { it }) return true

    for (i in board.indices) {
        if (!board[i][column]) return false
    }
    return true
}

fun calculateScore(outcome: BingoOutcome): Int {
    val winnerBoard = outcome.winnerBoard
    var sum = 0

    for (i in winnerBoard.indices) {
        for (j in winnerBoard[0].indices) {
            if (!outcome.playableWinnerBoard[i][j]) sum += winnerBoard[i][j]
        }
    }

    return sum * outcome.lastDraw
}

object GiantSquidPartOne {
    fun bingo(): Bingo {
        val lines = GiantSquidPartOne::class.java.getResource("bingo.txt")!!
            .readText()
            .split(Regex("\n\n"))

        val draws = lines.first().split(',').map { it.toInt() }
        val boards = lines.drop(1).map { board ->
            board.split('\n').map {
                row -> row.split(' ').filterNot { it.isEmpty() }.map { it.toInt() }
            }
        }
        return Bingo(draws, boards)
    }
}

typealias Board = List<List<Int>>
typealias PlayableBoard = MutableList<MutableList<Boolean>>

data class Bingo(val draws: List<Int>, val boards: List<Board>)
data class BingoOutcome(val winnerBoard: Board, val playableWinnerBoard: PlayableBoard, val lastDraw: Int)