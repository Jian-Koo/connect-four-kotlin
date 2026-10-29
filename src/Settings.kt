package com.example.connectfour

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun settingsMenu(
    onNavigateToMenu: () -> Unit,
    onNavigateToPlayerOne: () -> Unit,
    onNavigateToPlayerTwo: () -> Unit,
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel
){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
    ){
        settingsBackButton(onNavigateToMenu)
        settingsMenuColumn(onNavigateToPlayerOne, onNavigateToPlayerTwo, gameViewModel, settingsViewModel)
    }
}

@Composable
fun settingsTitle(){
    Text(text = "Settings", fontSize = 40.sp, color = Color.White)
}

@Composable
fun playerOneTitle(){
    Text(text = "Player 1:", fontSize = 30.sp, color = Color.White)
}

@Composable
fun playerOneRow(viewModel: SettingsViewModel, onNavigateToPlayerOne: () -> Unit){
    Button(
        onClick = onNavigateToPlayerOne,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {

            //Spacer(modifier = Modifier.width(30.dp))
            Box(modifier = Modifier){
                Image(painter = painterResource(id = viewModel.picOne),
                    contentDescription = "Image",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp))
            }
            //Spacer(modifier = Modifier.width(30.dp))
            Text(text = viewModel.usernameOne, fontSize = 30.sp, color = Color.Black)
            //Spacer(modifier = Modifier.width(30.dp))
            Box(modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(viewModel.colourOne)){
            }
            //Spacer(modifier = Modifier.width(30.dp))
        }
    }
}

@Composable
fun playerTwoTitle(){
    Text(text = "Player 2:", fontSize = 30.sp, color = Color.White)
}

@Composable
fun playerTwoRow(viewModel: SettingsViewModel, onNavigateToPlayerTwo: () -> Unit){
    Button(onClick = onNavigateToPlayerTwo,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {

            //Spacer(modifier = Modifier.width(30.dp))
            Box(modifier = Modifier){
                Image(painter = painterResource(id = viewModel.picTwo),
                    contentDescription = "Image",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp))
            }
            //Spacer(modifier = Modifier.width(30.dp))
            Text(text = viewModel.usernameTwo, fontSize = 30.sp, color = Color.Black)
            //Spacer(modifier = Modifier.width(30.dp))
            Box(modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(viewModel.colourTwo)){
            }
            //Spacer(modifier = Modifier.width(30.dp))
        }
    }
}

@Composable
fun settingsBackButton(onNavigateToMenu: () -> Unit){
    // Back Button with circular background
    IconButton(
        onClick = onNavigateToMenu,
        modifier = Modifier
            .padding(16.dp)
            .size(60.dp)
            .background(color = Color.Gray, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft, // Use the arrow left icon
            contentDescription = "Done",
            tint = Color.White // Icon color
        )
    }
}

@Composable
fun gridTitle(viewModel: GameViewModel){
    Row(modifier = Modifier
        .fillMaxWidth()) {
        Text(text = "Grid Size: ", fontSize = 30.sp, color = Color.White)
        Text(text = "${viewModel.col} x ${viewModel.row}", fontSize = 30.sp, color = Color.White)
    }
}

@Composable
fun gridRow(viewModel: GameViewModel){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val gridButtonSize = minOf(screenWidth / 4, 120)

    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = {
            viewModel.col = 6
            viewModel.row = 5
            viewModel.reset()
        },
            modifier = Modifier.height(60.dp).width(gridButtonSize.dp)) {
            Text(text = "6 x 5")
        }
        Button(onClick = {
            viewModel.col = 7
            viewModel.row = 6
            viewModel.reset()
        },
            modifier = Modifier.height(60.dp).width(gridButtonSize.dp)) {
            Text(text = "7 x 6")
        }
        Button(onClick = {
            viewModel.col = 8
            viewModel.row = 7
            viewModel.reset()
        },
            modifier = Modifier.height(60.dp).width(gridButtonSize.dp)) {
            Text(text = "8 x 7")
        }
    }
}

@Composable
fun settingsMenuColumn(
    onNavigateToPlayerOne: () -> Unit,
    onNavigateToPlayerTwo: () -> Unit,
    gameViewModel: GameViewModel,
    settingsViewModel: SettingsViewModel
){
    // Source : https://developer.android.com/develop/ui/compose/lists
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            settingsTitle()
        }
        item {
            playerOneTitle()
        }
        item {
            playerOneRow(settingsViewModel, onNavigateToPlayerOne)
        }
        item {
            playerTwoTitle()
        }
        item {
            playerTwoRow(settingsViewModel, onNavigateToPlayerTwo)
        }
        item {
            gridTitle(gameViewModel)
        }
        item {
            gridRow(gameViewModel)
        }
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
