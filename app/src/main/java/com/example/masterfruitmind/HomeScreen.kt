package com.example.masterfruitmind

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(viewModel: GameManager, nav_controller: NavController) {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = ("MASTERMINDFRUITS !!"),
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(40.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.startGame()
                nav_controller.navigate("game")
            }) {
                Text("Start")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
            }) {
                Text("Quit")
            }
        }
    }
}