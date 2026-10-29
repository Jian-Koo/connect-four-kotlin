package com.example.connectfour

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class SettingsViewModel: ViewModel() {

    var usernameOne by mutableStateOf("Player One")
    var usernameTwo by mutableStateOf("Player Two")

    var picOne by mutableStateOf(R.drawable.triangleimg)
    var picTwo by mutableStateOf(R.drawable.circleimg)

    var colourOne by mutableStateOf(Color.Red)
    var colourTwo by mutableStateOf(Color.Yellow)


    fun updateColourOne(colour : Color) {
        // check to make sure the colour is not the same as colourTwo to avoid confusion
        if (colour != colourTwo) {
            colourOne = colour
        }
    }

    fun updateColourTwo(colour : Color) {
        // check to make sure the colour is not the same as colourOne to avoid confusion
        if (colour != colourOne) {
            colourTwo = colour
        }
    }
}