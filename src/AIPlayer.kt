package com.example.connectfour

class AIPlayer {

    private val maxDepth = 4 // it can be adjusted, higher value means ai considers further, but slower, vice versa

    fun getNextMove(
        board: List<List<Int>>,
        colCount: Int
    ): Int {
        // Pre-calculate the score board for the current state
        val scoreBoard = assignScoresToBoard(board, colCount)

        // First, try to block any immediate winning moves by the opponent
        val blockingMove = findBlockingMove(board, colCount)
        if (blockingMove != -1) {
            return blockingMove // if any is found, block it
        }

        // if no instant winning move is found for the human player, then it would use minimax
        // algorithm to choose for the next move.

        var bestScore = Int.MIN_VALUE
        var bestCol = -1

        // loop through every valid columns, and determine which column would provide the highest total score for AI player
        for (col in getValidColumns(board, colCount)) {
            // Simulate the move for the AI (Player 2)
            val newBoard = makeMove(board, col, 2)
            // Evaluate the move using Minimax with the precomputed scoreBoard
            val score = minimax(newBoard, maxDepth, Int.MIN_VALUE, Int.MAX_VALUE, false, colCount, scoreBoard)
            if (score > bestScore) {
                bestScore = score
                bestCol = col
            }
        }
        return bestCol
    }

    private fun getValidColumns(board: List<List<Int>>, colCount: Int): List<Int> {
        return (0 until colCount).filter { col -> board[0][col] == 0 }
    }

    private fun assignScoresToBoard(board: List<List<Int>>, colCount: Int): List<List<Int>> {
        val rowCount = board.size
        val scoredBoard = List(rowCount) { MutableList(colCount) { 0 } }

        for (row in 0 until rowCount) {
            for (col in 0 until colCount) {
                if (board[row][col] == 0) {
                    // calculate and assign score for every cells (it is better to occupy a cell with higher score)
                    // a score indicates that how many possible ways you can win with that cell
                    val score = calculatePotentialWinningWays(row, col, rowCount, colCount)
                    scoredBoard[row][col] = score
                }
            }
        }
        return scoredBoard
    }

    private fun calculatePotentialWinningWays(
        row: Int,
        col: Int,
        rowCount: Int,
        colCount: Int
    ): Int {
        var potentialWays = 0
        potentialWays += horizontalWinningWays(row, col, colCount)  // Horizontal ways
        potentialWays += verticalWinningWays(row, rowCount) // Vertical ways
        potentialWays += diagonalWinningWays(row, col, rowCount, colCount)  // Diagonal ways

        return potentialWays
    }

    private fun horizontalWinningWays(row: Int, col: Int, colCount: Int): Int {
        var count = 0
        val winLength = 4

        // Check all possible horizontal windows where this cell can be part of a winning line
        for (startCol in 0..(colCount - winLength)) {
            if (col in startCol until (startCol + winLength)) {
                count++
            }
        }

        return count
    }

    private fun verticalWinningWays(row: Int, rowCount: Int): Int {
        var count = 0
        val winLength = 4

        // Check all possible vertical windows where this cell can be part of a winning line
        for (startRow in 0..(rowCount - winLength)) {
            if (row in startRow until (startRow + winLength)) {
                count++
            }
        }

        return count
    }

    private fun diagonalWinningWays(row: Int, col: Int, rowCount: Int, colCount: Int): Int {
        var count = 0
        val winLength = 4

        // Check all down-right diagonals
        for (startRow in 0..(rowCount - winLength)) {
            for (startCol in 0..(colCount - winLength)) {
                if ((row - startRow) == (col - startCol) && row in startRow until (startRow + winLength)) {
                    count++
                }
            }
        }

        // Check all down-left diagonals
        for (startRow in 0..(rowCount - winLength)) {
            for (startCol in (winLength - 1) until colCount) {
                if ((row - startRow) == (startCol - col) && row in startRow until (startRow + winLength)) {
                    count++
                }
            }
        }

        return count
    }

    private fun minimax(
        board: List<List<Int>>,
        depth: Int,
        alpha: Int,
        beta: Int,
        isMaximisingPlayer: Boolean,
        colCount: Int,
        scoreBoard: List<List<Int>>
    ): Int {
        if (depth == 0) {
            return evaluateBoard(board, scoreBoard) // it has reached the deepest level, now evaluates the score
        }

        var newAlpha = alpha
        var newBeta = beta

        if (isMaximisingPlayer) {
            // AI is maximising player
            var maxEval = Int.MIN_VALUE

            for (col in getValidColumns(board, colCount)) {
                val newBoard = makeMove(board, col, 2) // simulate AI move
                val eval = minimax(newBoard, depth - 1, newAlpha, newBeta, false, colCount, scoreBoard)
                maxEval = maxOf(maxEval, eval)
                newAlpha = maxOf(newAlpha, eval)

                if (newBeta <= newAlpha) {
                    break // Beta cut-off, when human player can guarantee a worse outcome than AI next move
                }
            }
            return maxEval
        } else {
            // human is minimising player
            var minEval = Int.MAX_VALUE

            for (col in getValidColumns(board, colCount)) {
                val newBoard = makeMove(board, col, 1) // simulate human player move
                val eval = minimax(newBoard, depth - 1, newAlpha, newBeta, true, colCount, scoreBoard)

                // Prioritize blocking opponent's winning move
                if (winCheck(newBoard, 1)) {
                    return Int.MIN_VALUE // Block winning move
                }
                minEval = minOf(minEval, eval)
                newBeta = minOf(newBeta, eval)

                if (newBeta <= newAlpha) {
                    break // Alpha cut-off, when AI can guarantee a better outcome than human player next move
                }
            }
            return minEval
        }
    }

    private fun evaluateBoard(board: List<List<Int>>, scoreBoard: List<List<Int>>): Int{
        var score = 0
        for (row in board.indices) { // loop through board's rows
            for (col in board[row].indices) { // loop through board's columns
                //
                if (board[row][col] == 2) {
                    score += scoreBoard[row][col]
                } else if (board[row][col] == 1) {
                    score -= scoreBoard[row][col]
                }
            }
        }
        return score
    }

    private fun findBlockingMove(board: List<List<Int>>, colCount: Int): Int {
        for (col in getValidColumns(board, colCount)) {
            val simulatedBoard = makeMove(board, col, 1) // make a simulated move
            if (winCheck(simulatedBoard, 1)) { // and check if that move from human player will result a win
                return col // if it would, return a move to that particular column to block the win
            }
        }
        return -1
    }

    private fun makeMove(board: List<List<Int>>, col: Int, player: Int): List<List<Int>> {
        val newBoard = board.map { it.toMutableList() }
        for (row in newBoard.size - 1 downTo 0) {
            if (newBoard[row][col] == 0) {
                newBoard[row][col] = player
                break
            }
        }
        return newBoard
    }

    // Idea of checking is from : https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm
    fun winCheck(board: List<List<Int>>, player: Int): Boolean { // winning condition checking
        val row = board.size
        val col = board[0].size

        // Horizontal Check
        for (j in 0 until col - 3) {
            for (i in 0 until row) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true
                }
            }
        }
        // Vertical Check
        for (i in 0 until row - 3) {
            for (j in 0 until col) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
                    return true
                }
            }
        }
        // Ascending Diagonal Check
        for (i in 3 until row) {
            for (j in 0 until col - 3) {
                if (board[i][j] == player && board[i - 1][j + 1] == player && board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
                    return true
                }
            }
        }
        // Descending Diagonal Check
        for (i in 3 until row) {
            for (j in 3 until col) {
                if (board[i][j] == player && board[i - 1][j - 1] == player && board[i - 2][j - 2] == player && board[i - 3][j - 3] == player) {
                    return true
                }
            }
        }
        return false
    }
}
