package adventOfCode.day4

// Difficulty: trivial, just a small change compared to the first part

// https://adventofcode.com/2021/day/4#part2
fun main() {
    val bingo = GiantSquidPartOne.bingo()

    // given the scale of the problem, I'll just brute-force it, no need to be smart about it
    val playableBoards: List<PlayableBoard> = List(bingo.boards.size) {
        val cells = bingo.boards[0].size
        MutableList(cells) { MutableList(cells){ false } }
    }

    val lastWinner = determineLastWinningBoard(bingo, playableBoards)
    println(calculateScore(lastWinner))
}

// the method is bigger that I would normally allow one to be... but I'm not super motivated to factor it out more nicely ;)
fun determineLastWinningBoard(bingo: Bingo, playableBoards: List<PlayableBoard>): BingoOutcome {
    val draws = bingo.draws.iterator()
    var lastWinnerOutcome: BingoOutcome? = null
    val winnerBoards = mutableSetOf<Int>()

    while (draws.hasNext()) {
        val draw = draws.next()

        for ((boardIndex, board) in bingo.boards.withIndex()) {
            if (boardIndex in winnerBoards) continue

            for (i in board.indices) {
                for (j in board[0].indices) {
                    if (board[i][j] == draw) {
                        val playableBoard = playableBoards[boardIndex]
                        playableBoard[i][j] = true

                        if (hasCompleteRowOrColumn(playableBoard, i, j)) {
                            lastWinnerOutcome = BingoOutcome(board, playableBoard, draw)
                            winnerBoards.add(boardIndex)
                        }
                    }
                }
            }
        }
    }

    // assuming there's at least one winner
    return lastWinnerOutcome!!
}