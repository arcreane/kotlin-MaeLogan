package com.example.masterfruitmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.masterfruitmind.ui.theme.MasterFruitMindTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       var listFruits = initListFruit("json/fruits.json")
        var listGame = shuffleListFruit(listFruits)
        super.onCreate(savedInstanceState)
        setContent {
            MasterFruitMindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
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

    fun shuffleListFruit(listFruits: List<fruits>): List<fruits> {
        return listFruits.shuffled().take(4)
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MasterFruitMindTheme {
        Greeting("Android")
    }
}