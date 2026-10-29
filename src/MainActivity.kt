package com.example.connectfour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.connectfour.ui.theme.ConnectFourTheme

class MainActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectFourTheme {
                Surface( // give an outline of the background colour and size
                    modifier = Modifier.fillMaxSize(),
                    color = Color.DarkGray
                ) {
                    // this makes the program always start at Main Menu screen
                    var currentScreen by rememberSaveable { mutableStateOf("menu") }

                    // menu = menu page, game = game board page, stats = stat page, settings = settings page
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (currentScreen) {
                            // there are four navigation buttons in menu page so there are four arguments
                            "menu" -> mainMenu(
                                // if single is chosen, it will setPVEMode as true in GameViewModel and affect
                                // the way of player 2 moves (moves randomly instead of needing a real human to
                                // click a column
                                onNavigateSinglePlayer = {
                                    currentScreen = "game"
                                    gameViewModel.setPVEMode(true)},
                                // normal 2 players mode, PVE mode is set to false
                                onNavigateMultiPlayer = {
                                    currentScreen = "game"
                                    gameViewModel.setPVEMode(false)},
                                // go to statistics screen
                                onNavigateStats = { currentScreen = "stats" },
                                // go to settings screen
                                onNavigateToSettings = { currentScreen = "settings" },
                                gameViewModel,
                                settingsViewModel
                            )
                            // while in GamePlay.kt, there is a button on left to corner which navigates to menu
                            // and this logic applies to stats and settings pages
                            "game" -> gameBoard(viewModel = gameViewModel, settingsViewModel = settingsViewModel, onNavigateToMenu = { currentScreen = "menu" })
                            "stats" -> statDisplay(viewModel = gameViewModel, onNavigateToMenu = { currentScreen = "menu" })
                            "settings" -> settingsMenu(
                                onNavigateToMenu = { currentScreen = "menu" },
                                onNavigateToPlayerOne = { currentScreen = "playerOne" },
                                onNavigateToPlayerTwo = { currentScreen = "playerTwo" },
                                gameViewModel, settingsViewModel
                            )
                            "playerOne" -> playerOneSettingsMenu(onNavigateToSettings = { currentScreen = "settings" }, settingsViewModel)
                            "playerTwo" -> playerTwoSettingsMenu(onNavigateToSettings = { currentScreen = "settings" }, settingsViewModel)
                        }
                    }
                }
            }
        }
    }
}
