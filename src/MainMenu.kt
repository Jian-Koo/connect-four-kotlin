package com.example.connectfour

import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun mainMenu(
    onNavigateSinglePlayer: () -> Unit,
    onNavigateMultiPlayer: () -> Unit,
    onNavigateStats: () -> Unit,
    onNavigateToSettings: () -> Unit,
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel
){  // Puts the above two composables together for previewing and deployment
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center
    ){
        //Spacer(modifier = Modifier
        //    .height(30.dp))
        menuTitle(settingsViewModel)
        menuButtons(
            onNavigateSinglePlayer = {
                onNavigateSinglePlayer() },
            onNavigateMultiPlayer = {
                onNavigateMultiPlayer() },
            onNavigateStats = onNavigateStats,
            onNavigateToSettings = onNavigateToSettings,
            viewModel = gameViewModel
        )
    }
}

@Composable
fun menuTitle(settingsViewModel: SettingsViewModel){  // Contains the title at the top of the screen
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    // Adjust font size based on screen width and height
    val baseFontSize = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        screenHeight / 5 // slightly smaller font size in landscape mode
    } else {
        screenWidth / 5 // adjust this division for better fit
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            buildAnnotatedString {  // Builds the string in 3 parts, allowing for separate colours and font sizes. Much of this code was copied from the Android Dev documentation: https://developer.android.com/develop/ui/compose/text/style-text
                withStyle(style = SpanStyle(color = settingsViewModel.colourOne, fontSize = baseFontSize.sp)) {
                    append("C")
                }
                withStyle(style = SpanStyle(fontSize = (baseFontSize * 0.8).sp)) {
                    append("onnect ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = settingsViewModel.colourTwo, fontSize = baseFontSize.sp)) {
                    append("4")
                }
            })
    }
}

@Composable
fun menuButtons(
    onNavigateSinglePlayer: () -> Unit,
    onNavigateMultiPlayer: () -> Unit,
    onNavigateStats: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: GameViewModel
){  // The four menu buttons, arranged in a column with spaces between them to allow adjustments in positioning
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier
                .height(30.dp)
                .weight(0.5f))
            Button(
                modifier = Modifier.width(140.dp),
                onClick = {
                    if (!viewModel.isPVE) {
                        viewModel.reset()
                    }
                    onNavigateSinglePlayer()
                } ){
                Text(text = "Single Player")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.width(140.dp),
                onClick = {
                    if (viewModel.isPVE) {
                        viewModel.reset()
                    }
                    onNavigateMultiPlayer()} ){
                Text(text = "Multi Player")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.width(140.dp),
                onClick = onNavigateStats){
                Text(text = "Statistics")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.width(140.dp),
                onClick = onNavigateToSettings ){
                Text(text = "Settings")
            }
            Spacer(modifier = Modifier
                .height(30.dp)
                .weight(1f))
        }
    }
}
