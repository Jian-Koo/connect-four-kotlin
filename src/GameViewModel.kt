package com.example.connectfour

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import java.text.DecimalFormat

class GameViewModel : ViewModel() {
    var row by mutableStateOf(6)
    var col by mutableStateOf(7)
    var winner by mutableStateOf(0) // 0 for no winner, 1 for Player 1, 2 for Player 2, 3 for draw
    var board by mutableStateOf(  // Use mutableStateOf to ensure state changes trigger recomposition
        List(row) { MutableList(col) { 0 } }
    )
    var isPVE by mutableStateOf(false)

    private var aiPlayer = AIPlayer()
    private var turns by mutableStateOf(0)
    private var moves by mutableStateOf(0)
    private var moveHistory = mutableListOf<Pair<Int, Int>>()
    private var totalGamePlayed by mutableStateOf(0)
    private var p1Win by mutableStateOf(0)
    private var p2Win by mutableStateOf(0)
    private var drawGame by mutableStateOf(0)

    fun whichPlayerTurn(): Int {
        return (turns % 2) + 1 // this ensure the value is always always 1 or 2
    }

    fun makeMove(column: Int) {
        if (winner != 0) return // No moves should be made if there's already a winner

        for (i in row - 1 downTo 0) {
            if (board[i][column] == 0) {
                // Update the board state
                board[i][column] = whichPlayerTurn()
                moves++

                // Record the move, basically it adds Pair(row, col) into the list
                // every time it is retrieved, it sets the board[row][col] to 0
                moveHistory.add(i to column)

                // Check if the current player has won
                if (aiPlayer.winCheck(board, whichPlayerTurn())) {
                    winner = whichPlayerTurn()
                    if (winner == 1) {
                        p1Win++
                    } else { // winner == 2
                        p2Win++
                    }
                    totalGamePlayed++
                    return
                }
                turns++

                if (turns >= totalAvailableMove()) {
                    winner = 3 // it's a draw
                    totalGamePlayed++
                    drawGame++
                    return
                }
                makeAIMove()
                break
            }
        }
    }

    private fun makeAIMove() {
        if (isPVE && whichPlayerTurn() == 2) {
            var aiMove = -1
            aiMove = aiPlayer.getNextMove(board, col)

            if (aiMove != -1) {
                makeMove(aiMove)
            }
        }
    }

    fun undo() {
        // Define how many moves to undo (1 for PvP, 2 for PvE)
        val undoCount = if (isPVE) 2 else 1

        for (i in 1..undoCount) {
            if (moveHistory.isNotEmpty() && winner == 0) {
                val (lastRow, lastCol) = moveHistory.removeAt(moveHistory.lastIndex)
                // Reset the last move
                board[lastRow][lastCol] = 0
                turns--
                moves--
            }
        }
    }

    fun reset() {
        turns = 0
        winner = 0
        moves = 0
        board = List(row) { MutableList(col) { 0 } }
        moveHistory.clear()
    }

    fun totalAvailableMove(): Int {
        return row * col
    }

    fun moveMade(): Int {
        return moves
    }

    fun totalGamePlayed(): Int {
        return totalGamePlayed
    }

    fun p1WinAmount(): Int {
        return p1Win
    }

    fun p2WinAmount(): Int {
        return p2Win
    }

    fun p1LoseAmount(): Int {
        return p2Win
    }

    fun p2LoseAmount(): Int {
        return p1Win
    }

    fun drawAmount(): Int {
        return drawGame
    }

    fun p1WinRate(): Double {
        if (p1Win == 0) {
            return 0.0
        }
        val rate = (p1Win.toDouble() / totalGamePlayed.toDouble()) * 100
        val formatRate = DecimalFormat("#.##")
        return formatRate.format(rate).toDouble()
    }

    fun p2WinRate(): Double {
        if (p2Win == 0) {
            return 0.0
        }
        val rate = (p2Win.toDouble() / totalGamePlayed.toDouble()) * 100
        val formatRate = DecimalFormat("#.##")
        return formatRate.format(rate).toDouble()
    }

    fun drawRate(): Double {
        if (drawGame == 0) {
            return 0.0
        }
        val rate = (drawGame.toDouble() / totalGamePlayed.toDouble()) * 100
        val formatRate = DecimalFormat("#.##")
        return formatRate.format(rate).toDouble()
    }

    fun setPVEMode(singlePlayer: Boolean) {
        isPVE = singlePlayer // true = vs AI, false = PvP
    }
}
