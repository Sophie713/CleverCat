package com.example.clevercat.activityMain.viewModels

import androidx.lifecycle.ViewModel
import com.example.clevercat.app.prefs
import com.example.clevercat.dataClasses.NumberItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor() : ViewModel() {
    private var numbersArray = arrayListOf<NumberItem>()

    fun addNumbers(howManyLines: Int) {
        //get latest id
        var numberId = prefs.latestId
        //error or first game
        if (numberId == null || numberId == -1) {
            numbersArray.clear()
            numberId = 0
        }
        //todo if too many numbers, show dialog
        //add 9 more numbers to ensure that when adding more, neighbours will match
        for (i in 1..((howManyLines + 1) * 9)) {
            val newNumber: Int = ((Math.random() * 9) + 1).toInt()

            /** I need to get the index of the number I am adding
             *  index will be length - count, I am getting it before adding the number
             *  so the index will be equal to the current size */
            numbersArray.add(
                NumberItem(
                    id = numberId,
                    numberValue = newNumber,
                    rightNeighbour = numberId + 1,
                    topNeighbour = numberId - 9,
                    bottomNeighbour = numberId + 9,
                    leftNeighbour = numberId - 1,
                )
            )
            numberId++
        }

        prefs.latestId = numberId
    }

    fun updateNumber(numberItem: NumberItem) {
        val index = numbersArray.indexOf(numberItem)
        numbersArray[index] = numberItem
    }

    fun setNumbersArray(savedGame: ArrayList<NumberItem>) {
        numbersArray.clear()
        numbersArray.addAll(savedGame)
        prefs.latestId = (numbersArray.last().id+1)
    }

    fun removeNumber(index: Int) {
        numbersArray.removeAt(index)
    }

    fun getNumbersArrayFull(): ArrayList<NumberItem> {
        return numbersArray
    }

    fun loadGame() {
        val type = object : TypeToken<List<NumberItem?>?>() {}.type
        val game: ArrayList<NumberItem> = Gson().fromJson(prefs.gameState, type)
        setNumbersArray(game)
    }

    fun saveGame() {
        prefs.gameState = Gson().toJson(numbersArray)
    }

    fun resetGame() {
        numbersArray.clear()
        prefs.gameState = "{}"
        prefs.latestId = 0
        addNumbers(10)
    }
}