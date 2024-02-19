package com.example.masterfruitmind

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameManager(val ListFruit: List<fruits>) {

    val historyListFruit = MutableLiveData<List<List<fruits>>>(emptyList())
    val historyGuessFruit = MutableLiveData<List<List<Int>>>(emptyList())
    var shuffleFruits = listOf<fruits>()
    var life = MutableLiveData<Int>()


    fun shuffleListFruit(listFruits: List<fruits>){
        shuffleFruits = listFruits.shuffled().take(4)
    }

    fun startGame(){
        shuffleListFruit(ListFruit);
        life.value = 10;
    }

    fun restartGame(){
        historyGuessFruit.value = emptyList()
        historyListFruit.value = emptyList()
        startGame()
    }

    fun giveFirstHint(){
        if(life.value?.minus(3)!! < 0){

        }
        life.value = life.value?.minus(3)

    }


    fun giveSecondHint(){
        if(life.value?.minus(5)!! < 0) {

        }
        life.value = life.value?.minus(5)

    }

    fun makeGuess(listFruitsFromUser: List<fruits>) : Boolean{
        val guess = GuessList(listFruitsFromUser);
        if(guess.all{it == 1}) {
            return true
        }
        historyListFruit.value = historyListFruit.value?.plus(listOf(listFruitsFromUser))

        historyGuessFruit.value = historyGuessFruit.value?.plus(listOf(guess))
        life.value = life.value?.minus(1)
        println(life.value)
        if(life.value!! <= 0){
        }
        println(historyListFruit.value)
        return false
    }


    fun GuessList(listFruitsFromUser: List<fruits>): MutableList<Int>{
        val result = mutableListOf<Int>()
        listFruitsFromUser.forEachIndexed { index,element ->
            if (element !in ListFruit) {
                if(listFruitsFromUser[index] == ListFruit[index]){
                    result.add(1)
                }
                else{
                    result.add(2)
                }
            }
            else{
                result.add(0)
            }
        }
        return result
    }

}