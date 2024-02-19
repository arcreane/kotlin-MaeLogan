package com.example.masterfruitmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav_Controller = rememberNavController()
            val viewModel = GameManager(initListFruit("json/fruits.json"))
            NavHost(nav_Controller, startDestination = "home") {
                composable("home") {HomeScreen(viewModel = viewModel, nav_Controller)}
                composable("game") { GameScreen(viewModel = viewModel, nav_Controller)}
            }
        }
    }

    fun initListFruit(pathFile: String): List<fruits>{
        var jsonFile = assets.open(pathFile)
        var fruits = jsonFile.bufferedReader().use { it.readText() }
        val gson = Gson()
        val fruitsArray = gson.fromJson(fruits, Array<fruits>::class.java)
        return fruitsArray.toList()
    }
}



