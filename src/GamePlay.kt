package com.example.connectfour

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun gameBoard(viewModel: GameViewModel, settingsViewModel: SettingsViewModel, onNavigateToMenu: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        landscapeLayout(viewModel, settingsViewModel, onNavigateToMenu)
    } else {
        portraitLayout(viewModel, settingsViewModel, onNavigateToMenu)
    }
}

@Composable
fun gameTitle(viewModel: GameViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val titleFontSize = minOf(screenWidth / 12, 30)
    val gameTitle = if (viewModel.isPVE) "Single Player" else "Multi Player"
    Text (
        text = gameTitle,
        color = Color.White,
        fontSize = titleFontSize.sp,
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun landscapeLayout(viewModel: GameViewModel, settingsViewModel: SettingsViewModel, onNavigateToMenu: () -> Unit) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box( // this is the section with the home button on left top corner
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            // Menu Button with circular background
            IconButton(
                onClick = onNavigateToMenu,
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp)
                    .background(color = Color.Gray, shape = CircleShape)
                    .align(Alignment.TopStart) // Keep the button in the top left corner
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft, // Use the arrow left // icon
                    contentDescription = "Menu",
                    tint = Color.White // Icon color
                )
            }

            // Game Title centered in the Box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                gameTitle(viewModel)
            }
        }

        Column( // middle game board section
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight()
                .weight(3f)
        ) {
            moveDisplay(viewModel)
            playerTurnIndicator(viewModel.winner, viewModel.whichPlayerTurn(), settingsViewModel)
            gameGrid(viewModel, settingsViewModel)
        }

        Column( // right section with undo and reset buttons
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            controlButton(text = "Undo", onClick = { viewModel.undo() })
            controlButton(text = "Reset", onClick = { viewModel.reset() })
        }
    }
}

@Composable
fun portraitLayout(viewModel: GameViewModel, settingsViewModel: SettingsViewModel, onNavigateToMenu: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row( // Row at the top with the button
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onNavigateToMenu,
                modifier = Modifier
                    .size(60.dp) // Adjust size as needed
                    .background(color = Color.Gray, shape = CircleShape) // Circular background
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft, // Use the arrow left icon
                    contentDescription = "Menu",
                    tint = Color.White // Icon color
                )
            }
        }

        Column( // this column contains the move indicator, turns indicator text, and game board itself
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) { // right here
            gameTitle(viewModel)
            moveDisplay(viewModel)
            playerTurnIndicator(viewModel.winner, viewModel.whichPlayerTurn(), settingsViewModel)
            gameGrid(viewModel, settingsViewModel)

            Row( // Undo and reset buttons in a row at the bottom of the game board section
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                controlButton(text = "Undo", onClick = { viewModel.undo() })
                controlButton(text = "Reset", onClick = { viewModel.reset() })
            }
        }
    }
}

@Composable
fun moveDisplay(viewModel: GameViewModel) {
    val movesMade = viewModel.moveMade()
    val totalMove = viewModel.totalAvailableMove()
    Row (
        horizontalArrangement = Arrangement.Center,
    ) {
        Text (
            text = "Moves : $movesMade / $totalMove",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun playerTurnIndicator(winner: Int, currentPlayer: Int, settingsViewModel: SettingsViewModel) {
    val player1Colour = settingsViewModel.colourOne
    val player2Colour = settingsViewModel.colourTwo
    val playerIcon = when {
        winner == 1 || currentPlayer == 1 -> settingsViewModel.picOne
        winner == 2 || currentPlayer == 2 -> settingsViewModel.picTwo
        else -> settingsViewModel.picOne // Default or neutral icon
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        // Add image based on the current player or winner
        if (winner == 3) { // If draw, show both player icons
            Image(
                painter = painterResource(id = settingsViewModel.picOne),
                contentDescription = "Player 1's Icon",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp)
            )
            Image(
                painter = painterResource(id = settingsViewModel.picTwo),
                contentDescription = "Player 2's Icon",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp)
            )
        } else {
            Image(
                painter = painterResource(id = playerIcon),
                contentDescription = "Player's Icon",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp)
            )
        }

        Text(
            text = when (winner) {
                0 -> {
                    when (currentPlayer) {
                        1 -> "${settingsViewModel.usernameOne}'s Turn"
                        else -> "${settingsViewModel.usernameTwo}'s Turn"
                    }
                }
                3 -> "It's a Draw!"
                else -> {
                    when (winner) {
                        1 -> "${settingsViewModel.usernameOne} Wins"
                        else -> "${settingsViewModel.usernameTwo} Wins"
                    }
                }
            },
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        if (winner == 3) { // if draw, show both circles
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(player1Colour, shape = CircleShape)
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(player2Colour, shape = CircleShape)
            )
        } else if (winner in 1..2) { // if there is a winner
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = if (winner == 1) player1Colour else player2Colour,
                        shape = CircleShape
                    )
            )
        } else { // normal turn
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = if (currentPlayer == 1) player1Colour else player2Colour,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun gameGrid(viewModel: GameViewModel, settingsViewModel: SettingsViewModel) {
    val grid = viewModel.board
    val col = viewModel.col
    val row = viewModel.row
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val cellSize = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        screenHeight / (row + 3) // slightly smaller in landscape because of lower height
    } else {
        screenWidth / (col + 2)
    }

    Box( // the base board which contains all the cells
        modifier = Modifier
            .background(Color.Gray, shape = RoundedCornerShape(24.dp))
            .padding(12.dp)
            .wrapContentSize()
    ) {
        Row( // a row of columns, just one row
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (j in 0 until col) {
                Column( // for every column, insert all the cells
                    // the amount of cells for a column = the number of row
                    modifier = Modifier.clickable { viewModel.makeMove(j) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in 0 until row) {
                        val cellColor = when (grid[i][j]) {
                            1 -> settingsViewModel.colourOne
                            2 -> settingsViewModel.colourTwo
                            else -> Color.White
                        }
                        Box( // a box to contain a single circle cell
                            modifier = Modifier
                                .size(cellSize.dp)
                                .padding(2.dp)
                                .background(color = cellColor, shape = CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun controlButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(20.dp)
            .size(width = 120.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6089D2))
    ) {
        Text(text = text, color = Color.White, fontSize = 15.sp)
    }
}

@Preview
@Composable
fun GameBoardPreview() {
    val gameViewModel = GameViewModel()
    val settingsViewModel = SettingsViewModel()
    gameBoard(gameViewModel, settingsViewModel, onNavigateToMenu = {})
}