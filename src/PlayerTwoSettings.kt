package com.example.connectfour

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun playerTwoSettingsMenu(
    onNavigateToSettings: () -> Unit,
    settingsViewModel: SettingsViewModel
){
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
    ){
        playerTwoBackButton(onNavigateToSettings)
        lazyPlayerTwo(settingsViewModel)
    }
}

@Composable
fun playerTwoBackButton(onNavigateToSettings: () -> Unit) {
    IconButton(
        onClick = onNavigateToSettings,
        modifier = Modifier
            .padding(16.dp)
            .size(60.dp)
            .background(color = Color.Gray, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft, // Use the arrow left icon
            contentDescription = "Back",
            tint = Color.White // Icon color
        )
    }

}

@Composable
fun playerTwoPageTitle(viewModel: SettingsViewModel) {
    // Player 2 Title
    Text(text = "Player 2", fontSize = 40.sp, color = Color.White,
        modifier = Modifier.padding(bottom = 4.dp))
}

@Composable
fun playerTwoName(viewModel: SettingsViewModel) {
    // Name text edit row
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Name:  ", fontSize = 30.sp, color = Color.White)
        TextField(value = viewModel.usernameTwo,
            onValueChange = {text->viewModel.usernameTwo = text})
    }
}

@Composable
fun playerTwoAvatarShowing(viewModel: SettingsViewModel) {
    // Avatar showing row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Avatar: ",
            fontSize = 30.sp,
            color = Color.White
        )
        Box(modifier = Modifier) {
            Image(painter = painterResource(id = viewModel.picTwo),
                contentDescription = "Image",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp))
        }
    }
}

@Composable
fun playerTwoAvatarChoosing(viewModel: SettingsViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val avatarButtonSize = minOf(screenWidth / 10, 40)
    // Avatar choosing row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly, // Ensure equal spacing
        verticalAlignment = Alignment.CenterVertically
    ) {
        playerTwoAvatarOption(
            icon = painterResource(id = R.drawable.arrowimg),
            onClick = { viewModel.picTwo = R.drawable.arrowimg },
            size = avatarButtonSize.dp
        )
        playerTwoAvatarOption(
            icon = painterResource(id = R.drawable.circleimg),
            onClick = { viewModel.picTwo = R.drawable.circleimg },
            size = avatarButtonSize.dp
        )
        playerTwoAvatarOption(
            icon = painterResource(id = R.drawable.eqtriimg),
            onClick = { viewModel.picTwo = R.drawable.eqtriimg },
            size = avatarButtonSize.dp
        )
        playerTwoAvatarOption(
            icon = painterResource(id = R.drawable.starimg),
            onClick = { viewModel.picTwo = R.drawable.starimg },
            size = avatarButtonSize.dp
        )
        playerTwoAvatarOption(
            icon = painterResource(id = R.drawable.triangleimg),
            onClick = { viewModel.picTwo = R.drawable.triangleimg },
            size = avatarButtonSize.dp
        )
    }
}

@Composable
fun playerTwoAvatarOption(
    icon: Painter,
    onClick: () -> Unit,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clickable(onClick = onClick)
            .background(Color.DarkGray)
            .padding(4.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null, // For icons, you can use null for content description
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun playerTwoDiscShowing(viewModel: SettingsViewModel) {
    // Disc Colour showing row
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Disc Colour: ", fontSize = 30.sp, color = Color.White)
        Box(modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(viewModel.colourTwo)){
        }
    }
}

@Composable
fun playerTwoDiscChoosing(viewModel: SettingsViewModel) {
    // Disc Colour choosing row
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Button(
            onClick = {viewModel.updateColourTwo(Color.Blue)},
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {}
        Button(
            onClick = {viewModel.updateColourTwo(Color.Yellow)},
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {}
        Button(
            onClick = {viewModel.updateColourTwo(Color.Green)},
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {}
        Button(
            onClick = {viewModel.updateColourTwo(Color.Red)},
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {}
        Button(
            onClick = {viewModel.updateColourTwo(Color.Magenta)},
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {}
    }
}

@Composable
fun lazyPlayerTwo(settingsViewModel: SettingsViewModel){
    // Source : https://developer.android.com/develop/ui/compose/lists
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ){
        item {
            playerTwoPageTitle(settingsViewModel)
        }
        item {
            playerTwoName(settingsViewModel)
        }
        item {
            playerTwoAvatarShowing(settingsViewModel)
        }
        item {
            playerTwoAvatarChoosing(settingsViewModel)
        }
        item {
            playerTwoDiscShowing(settingsViewModel)
        }
        item {
            playerTwoDiscChoosing(settingsViewModel)
        }
    }
}