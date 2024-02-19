package com.example.masterfruitmind

import androidx.lifecycle.MutableLiveData

class GameManager(val ListFruit: List<fruits>) {

    val historyListFruit = MutableLiveData<List<List<fruits>>>(emptyList())
    val historyGuessFruit = MutableLiveData<List<List<Int>>>(emptyList())
    var shuffleFruits = listOf<fruits>()
    var life = MutableLiveData<Int>()
    var win = MutableLiveData<Boolean>()

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
        win.value = false
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
            win.value = true
            return true
        }
        historyListFruit.value = historyListFruit.value?.plus(listOf(listFruitsFromUser))

        historyGuessFruit.value = historyGuessFruit.value?.plus(listOf(guess))
        life.value = life.value?.minus(1)
        return false
    }


    fun GuessList(listFruitsFromUser: List<fruits>): MutableList<Int>{
        val result = mutableListOf<Int>()
        listFruitsFromUser.forEachIndexed { index,element ->
            if (element !in shuffleFruits) {
                if(listFruitsFromUser[index].name == shuffleFruits[index].name){
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

    fun calculateGuessResult(guess: List<Int>): String {
        var correctPlacement = 0
        var wrongPlacement = 0
        var notPresent = 0

        guess.forEach{
            if(it == 1)
                correctPlacement += 1
            else if(it == 2){
                wrongPlacement += 1
            }
            else{
                notPresent += 1
            }
        }
        return "$correctPlacement fruits bien placé(s), $wrongPlacement fruits mal placé(s) et $notPresent fruit(s) non présent(s)"
    }

}



