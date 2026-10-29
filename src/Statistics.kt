package com.example.connectfour

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun statDisplay(viewModel: GameViewModel, onNavigateToMenu: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        statContentLandscape(viewModel, onNavigateToMenu)
    } else {
        statContentPortrait(viewModel, onNavigateToMenu)
    }

}

@Composable
private fun statContentPortrait(viewModel: GameViewModel, onNavigateToMenu: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            IconButton(
                onClick = onNavigateToMenu,
                modifier = Modifier
                    .size(60.dp)
                    .background(color = Color.Gray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Game Statistics",
                color = Color.White,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total Games Played: ${viewModel.totalGamePlayed()}",
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "P1's Wins: ${viewModel.p1WinAmount()}",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "P1's Loses: ${viewModel.p1LoseAmount()}",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "P1's Win Rate: ${viewModel.p1WinRate()}%",
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "P2's Wins: ${viewModel.p2WinAmount()}",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "P2's Loses: ${viewModel.p2LoseAmount()}",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "P2's Win Rate: ${viewModel.p2WinRate()}%",
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Draw Games: ${viewModel.drawAmount()}",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "Draw Rate: ${viewModel.drawRate()}%",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun statContentLandscape(viewModel: GameViewModel, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(60.dp)
                    .background(color = Color.Gray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = 32.dp)
        ) {
            Text(
                text = "Game Statistics",
                color = Color.White,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Total Games Played: ${viewModel.totalGamePlayed()}",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(end = 48.dp)
                ) {
                    Text(
                        text = "P1's Wins: ${viewModel.p1WinAmount()}",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "P1's Loses: ${viewModel.p1LoseAmount()}",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "P1's Win Rate: ${viewModel.p1WinRate()}%",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(end = 48.dp)
                ) {
                    Text(
                        text = "P2's Wins: ${viewModel.p2WinAmount()}",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "P2's Loses: ${viewModel.p2LoseAmount()}",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "P2's Win Rate: ${viewModel.p2WinRate()}%",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Draw Games: ${viewModel.drawAmount()}",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Draw Rate: ${viewModel.drawRate()}%",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun statDisplayView() {
    val gameViewModel = GameViewModel()
    statDisplay(gameViewModel, onNavigateToMenu = {})
}