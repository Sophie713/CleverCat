package com.example.clevercat.activityMain.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clevercat.app.prefs
import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.room.repository.NumberItemsRepository
import com.example.clevercat.sharedClasses.constants.Constants
import com.example.clevercat.sharedClasses.extentions.getById
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
   private val numbersRepository: NumberItemsRepository
) : ViewModel() {
    private var numbersArray = mutableStateListOf<NumberItem>()

    val viewModelEvents = MutableLiveData<ViewModelEvents>()

    enum class ViewModelEvents { NO_MATCH_FOUND, LOAD_GAME_FAILED }

    //has nonsense value option so we dont have to worry about nullability
    private var previouslyClickedNumber = Constants.NON_EXISTING_NUMBER
    private var numberOfClicksAfterHint = 0
    private var hintNumber1: NumberItem? = null
    private var hintNumber2: NumberItem? = null
    fun addNumbers(howManyLines: Int) {
        //get latest id
        var numberId = prefs.latestId
        viewModelScope.launch(IO) {//todo handler
           val last = numbersRepository.getLast().id
            Log.e(Constants.LOG_TAG, last.toString())
        }
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
        if (index > 0) {
            numbersArray.removeAt(index)
            numbersArray.add(index, numberItem)
        }
    }

    private fun setNumbersArray(savedGame: ArrayList<NumberItem>) {
        numbersArray.clear()
        if (savedGame.isNullOrEmpty()) {
            resetGame()
        } else {
            numbersArray.addAll(savedGame)
            prefs.latestId = (numbersArray.last().id.plus(1))
        }
    }

    fun getNumbersArray(): SnapshotStateList<NumberItem> {
        return numbersArray
    }

    fun loadGame() {
        viewModelScope.launch(IO) {//todo handler
            val list = numbersRepository.getAllNumbers()
            list.forEach {
                Log.e(Constants.LOG_TAG, it.toString())
            }

        }
        val type = object : TypeToken<ArrayList<NumberItem?>?>() {}.type
        val game: ArrayList<NumberItem> = Gson().fromJson(prefs.gameState, type)
        setNumbersArray(game)
    }

    fun saveGame() {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.gameState = Gson().toJson(numbersArray)
            numbersRepository.upsertItems(numbersArray)
        }

    }

    fun resetGame() {
        numbersArray.clear()
        prefs.gameState = "[]"
        prefs.latestId = 0
        addNumbers(10)
    }

    fun showHint() {
        numberOfClicksAfterHint = 0
        var isThereMatch = false
        //subarray to avoid matches with invisible numbers
        val matchSubArray: ArrayList<NumberItem> = arrayListOf<NumberItem>()
        matchSubArray.addAll(numbersArray.subList(0, numbersArray.size - 9))
        for (index in 0 until matchSubArray.size) {
            val numberItem = matchSubArray[index]
            if (numberItem.isNumberStillInGame) {
                val rightNeighbour = matchSubArray.getById(numberItem.rightNeighbour)
                if (rightNeighbour != null &&
                    (rightNeighbour.numberValue == numberItem.numberValue
                            || (rightNeighbour.numberValue?.plus(numberItem.numberValue) == 10)
                            )
                ) {
                    showHintUI(numberItem, rightNeighbour, true)
                    isThereMatch = true
                    break
                }
                val bottomNeighbour = matchSubArray.getById(numberItem.bottomNeighbour)
                if (bottomNeighbour != null &&
                    (bottomNeighbour.numberValue == numberItem.numberValue
                            || (bottomNeighbour.numberValue?.plus(numberItem.numberValue) == 10)
                            )
                ) {
                    showHintUI(numberItem, bottomNeighbour, true)
                    isThereMatch = true
                    break
                }
            }
        }
        if (!isThereMatch) {
            viewModelEvents.value = ViewModelEvents.NO_MATCH_FOUND
        }
    }

    private fun showHintUI(firstNumber: NumberItem, secondNumber: NumberItem, isShow: Boolean) {
        firstNumber.isHint = isShow
        secondNumber.isHint = isShow
        if (isShow) {
            hintNumber1 = firstNumber
            hintNumber2 = secondNumber
            updateNumber(firstNumber)
            updateNumber(secondNumber)
        } else {
            updateNumber(firstNumber)
            updateNumber(secondNumber)
            hintNumber1 = null
            hintNumber2 = null
        }
    }

    fun onNumberClick(clickedNumber: NumberItem) {
        if (hintNumber1 != null) {
            numberOfClicksAfterHint++
            if (numberOfClicksAfterHint == 2) {
                numberOfClicksAfterHint = 0
                showHintUI(hintNumber1!!, hintNumber2!!, false)
            }
        }
        //do nothing if inactive number or the same as last clicked
        if (clickedNumber.isNumberStillInGame && previouslyClickedNumber.id != clickedNumber.id) {

            //1) there is a possible match
            if (previouslyClickedNumber.numberValue == clickedNumber.numberValue || previouslyClickedNumber.numberValue + clickedNumber.numberValue == 10) {
                // a) numbers are neighbours - MATCH !!
                if (clickedNumber.topNeighbour == previouslyClickedNumber.id
                    || clickedNumber.bottomNeighbour == previouslyClickedNumber.id
                    || clickedNumber.leftNeighbour == previouslyClickedNumber.id
                    || clickedNumber.rightNeighbour == previouslyClickedNumber.id
                ) {
                    markNumberAsMatched(clickedNumber)
                    markNumberAsMatched(previouslyClickedNumber)
                    previouslyClickedNumber = Constants.NON_EXISTING_NUMBER
                }
                // b) numbers arent neighbours - update backgrounds to show newly selected number
                else {
                    //update UI to show chosen Number
                    previouslyClickedNumber.clearState()
                    updateNumber(previouslyClickedNumber)
                    clickedNumber.isSelected = true
                    previouslyClickedNumber = clickedNumber
                    updateNumber(clickedNumber)
                }
            }
            // 2. there is no possible match - update backgrounds to show newly selected number
            else {
                //update UI to show chosen Number
                previouslyClickedNumber.clearState()
                updateNumber(previouslyClickedNumber)//todo xyz fix
                clickedNumber.isSelected = true
                previouslyClickedNumber = clickedNumber
                updateNumber(clickedNumber)
            }
            logNumber(clickedNumber)
        }
    }

    private fun logNumber(clickedNumber: NumberItem) {
        Log.e(
            "xyz",
            "\nNUMBER: ${clickedNumber.numberValue}," +
                    "\nid: ${clickedNumber.id}," +
                    "\nright: ${clickedNumber.rightNeighbour} " +
                    "\nleft: ${clickedNumber.leftNeighbour} " +
                    "\ntop: ${clickedNumber.topNeighbour} " +
                    "\nbottom: ${clickedNumber.bottomNeighbour}"
        )
    }

    private fun markNumberAsMatched(removedNumber: NumberItem) {
        // FIRST NUMBER UPDATE
        val index = numbersArray.indexOf(removedNumber);
        removedNumber.isNumberStillInGame = false
        //find neighbours
        val rightNeighbour = numbersArray.getById(removedNumber.rightNeighbour)
        val leftNeighbour = numbersArray.getById(removedNumber.leftNeighbour)
        val topNeighbour = numbersArray.getById(removedNumber.topNeighbour)
        val bottomNeighbour = numbersArray.getById(removedNumber.bottomNeighbour)
        //update neighbour's values
        //right - update and send data to viewModel
        rightNeighbour?.let {
            it.leftNeighbour = removedNumber.leftNeighbour
        }
        //left - update and send data to viewModel
        leftNeighbour?.let {
            it.rightNeighbour = removedNumber.rightNeighbour
        }
        //top - update and send data to viewModel
        topNeighbour?.let {
            it.bottomNeighbour = removedNumber.bottomNeighbour
        }
        //bottom - update and send data to viewModel
        bottomNeighbour?.let {
            it.topNeighbour = removedNumber.topNeighbour
        }
        //remove line if all in line are matched or update view
        val indexOfFirstInLine = index - (index.rem(9))
        var isLast = true
        for (i in indexOfFirstInLine..(indexOfFirstInLine + 8)) {
            if (numbersArray[i].isNumberStillInGame) {
                isLast = false
            }
        }
        //if last in line remove the line and send update to viewModel
        if (isLast) {
            for (i in 0..8) {
                numbersArray.removeAt(indexOfFirstInLine)
            }
        }
        //update current number in view model if not removed
        else {
            //if number stays in the game = show it as white
            //remove neighbours from removed number just to make sure
            removedNumber.rightNeighbour = -1
            removedNumber.leftNeighbour = -1
            removedNumber.topNeighbour = -1
            removedNumber.bottomNeighbour = -1
            removedNumber.clearState()
            updateNumber(removedNumber)
        }

    }

}