package com.example.masterfruitmind

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(viewModel: GameManager, nav_controller: NavController) {
    val life by viewModel.life.observeAsState()
    val guessHistory by viewModel.historyGuessFruit.observeAsState()
    val resultHistory by viewModel.historyListFruit.observeAsState()
    var has_win by remember { mutableStateOf(false) }
    var isWinPopupVisible by remember { mutableStateOf(false) }
    println(viewModel.shuffleFruits)
    val allFruits = listOf(
        fruits("banane", false, true, R.drawable.banane),
        fruits("raisin", true, false, R.drawable.raisin),
        fruits("kiwi", false, true, R.drawable.kiwi),
        fruits("citron", true, true, R.drawable.citron),
        fruits("orange", true, true, R.drawable.orange),
        fruits("prune", true, false, R.drawable.prune),
        fruits("framboise", false, false, R.drawable.framboise),
        fruits("fraise", false, false, R.drawable.fraise),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "MasterMindFruits life = $life" ,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                )
            }

            resultHistory?.let { FruitsList(it) }

            Spacer(modifier = Modifier.weight(1f))
            BottomBar(
                fruitsList = allFruits,
                onFruitSelected = {},
                onGuessValidated = {
                    if(viewModel.makeGuess(it)){
                        has_win = true

                    }
                })
        }
    }

    if (has_win) {
        isWinPopupVisible = true
    }

    WinPopup(
        isWinPopupVisible = isWinPopupVisible,
        onRestartButtonClicked = { viewModel.restartGame() },
     { nav_controller.popBackStack() },
        viewModel = viewModel)


}


@Composable
fun WinPopup(
    isWinPopupVisible: Boolean,
    onRestartButtonClicked: () -> Unit,
    onQuitButtonClicked: () -> Unit,
    viewModel: GameManager
) {
    if (isWinPopupVisible) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Congratulations!") },
            text = { Text("You win!") },
            confirmButton = {
                Button(onClick = {

                    onRestartButtonClicked()
                }) {
                    Text("Restart")
                }
            },
            dismissButton = {
                Button(onClick = {
                    onQuitButtonClicked()
                }) {
                    Text("Quit")
                }
            }
        )
    }
}


@Composable
fun FruitsList(fruitsList: List<List<fruits>>) {
    Column(modifier = Modifier.padding(16.dp)) {
            fruitsList.withIndex().forEach { (index, fruitList) ->
                Text(
                    text = "Try number: ${index + 1} Fruits in this list:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    fruitList.forEach { fruit ->
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = fruit.pathImage),
                                contentDescription = fruit.name,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }


@Composable
fun InputRow(fruitsList: List<fruits>, onFruitSelected: (fruits) -> Unit) {
    var selectedFruit by remember { mutableStateOf<fruits?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val width = (1f / 5) * LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .width(width)
            .border(2.dp, Color.Black)
            .fillMaxHeight()
            .clickable(onClick = { expanded = true })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .weight(1f)
            ) {
                fruitsList.forEach { fruit ->
                    DropdownMenuItem(
                        onClick = {
                            selectedFruit = fruit
                            onFruitSelected(fruit)
                        },
                        text = { Text(text = fruit.name) },
                        leadingIcon = {
                                Image(
                                    painter = painterResource(id = fruit.pathImage),
                                    contentDescription = fruit.name,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 8.dp)
                                )

                        }
                    )
                }
            }
            if (selectedFruit != null) {
                Image(
                    painter = painterResource(id = selectedFruit!!.pathImage),
                    contentDescription = selectedFruit!!.name,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                )
            }else {
                Image(
                    painter = painterResource(id = R.drawable.blank_image),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

        }
    }
}

@Composable
fun BottomBar(
    fruitsList: List<fruits>,
    onFruitSelected: (fruits) -> Unit,
    onGuessValidated: (List<fruits>) -> Unit
) {
    var inputList = List(4) { fruits("", false, false, 0 )}.toMutableList()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color.Black)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(4) {
                    InputRow(fruitsList) { fruit ->
                        inputList[it] = fruit
                    }
                }
            }

            Button(
                onClick = {
                    if (inputList.none { it.name == "" } && inputList.size == 4) {
                        onGuessValidated(inputList)

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text("Validate")
            }
        }

    }
}