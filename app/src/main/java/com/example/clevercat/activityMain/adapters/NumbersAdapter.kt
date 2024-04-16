package com.example.clevercat.activityMain.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clevercat.R
import com.example.clevercat.activityMain.adapters.viewHolders.NumberCellViewHolder
import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.databinding.ItemNumberLayoutBinding
import com.example.clevercat.sharedClasses.constants.Constants.NON_EXISTING_NUMBER
import com.example.clevercat.sharedClasses.extentions.getById
import com.sophie.miller.clevercat.listener.NumbersUpdatesListener

class NumbersAdapter(val context: Context, val listener: NumbersUpdatesListener) :
    RecyclerView.Adapter<NumberCellViewHolder>() {

    private var numbersArray: ArrayList<NumberItem> = arrayListOf()

    //has nonsense value option so we dont have to worry about nullability
    private var previouslyClickedNumber = NON_EXISTING_NUMBER
    private var hint1PositionInAdapter: Int? = null
    private var hint2PositionInAdapter: Int? = null
    private var numberOfClicksAfterHint = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberCellViewHolder {
        val viewBinding = ItemNumberLayoutBinding.inflate(LayoutInflater.from(context))
        return NumberCellViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        //dont show last 9 numbers, they are generated just to keep track of matches
        val listActualLength = numbersArray.size
        return if (listActualLength > 9) {
            (listActualLength - 9)
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: NumberCellViewHolder, position: Int) {
        val thisNumber = numbersArray[position];
        //save position for later UI updates
        thisNumber.adapterPosition = holder.absoluteAdapterPosition
        //set new position
        holder.numberTextView.setText("${thisNumber.numberValue}")
        //set active colors
        if (thisNumber.isNumberStillInGame) {
            holder.numberTextView.setTextColor(-0x1000000) //BLACK
        } else {
            holder.numberTextView.setTextColor(-0x1) //WHITE
        }

        //show selected todo xyz make better colors
        if (numberOfClicksAfterHint < 2 && (holder.absoluteAdapterPosition == hint1PositionInAdapter || holder.absoluteAdapterPosition == hint2PositionInAdapter)) {
            holder.numberLayout.setBackgroundResource(R.color.purple_200)
        } else
            if (thisNumber.id == previouslyClickedNumber.id) {
                holder.numberLayout.setBackgroundResource(R.color.teal_200)//.setBackgroundResource(R.drawable.bckg_pink_black_border)
            } else {
                holder.numberLayout.setBackgroundResource(R.color.teal_700)//.setBackgroundResource(R.drawable.bckg_pink)
            }
//        // ** DEBUG **
//        holder.numberTextView.text = thisNumber.id.toString()
//        holder.start.text = thisNumber.leftNeighbour.toString()
//        holder.end.text = thisNumber.rightNeighbour.toString()
//        holder.top.text = thisNumber.topNeighbour.toString()
//        holder.bottom.text = thisNumber.bottomNeighbour.toString()
//        // ** DEBUG **
        holder.numberLayout.setOnClickListener { v ->
            //update position for later UI updates
            thisNumber.adapterPosition = holder.absoluteAdapterPosition
            //do nothing if inactive number or the same as last clicked
            if (thisNumber.isNumberStillInGame && thisNumber.id != previouslyClickedNumber.id) {

                //1) there is a possible match
                if (previouslyClickedNumber.numberValue == thisNumber.numberValue || previouslyClickedNumber.numberValue + thisNumber.numberValue == 10) {
                    // a) numbers are neighbours - MATCH !!
                    if (thisNumber.topNeighbour == previouslyClickedNumber.id
                        || thisNumber.bottomNeighbour == previouslyClickedNumber.id
                        || thisNumber.leftNeighbour == previouslyClickedNumber.id
                        || thisNumber.rightNeighbour == previouslyClickedNumber.id
                    ) {
                        markNumberAsMatched(thisNumber)
                        markNumberAsMatched(previouslyClickedNumber)
                        previouslyClickedNumber = NON_EXISTING_NUMBER
                    }
                    // b) numbers arent neighbours - update backgrounds to show newly selected number
                    else {
                        //save old index
                        val previousAdapterPosition = previouslyClickedNumber.adapterPosition
                        //update index to current number
                        previouslyClickedNumber = thisNumber
                        //now background will be reset to pink
                        notifyItemChanged(previousAdapterPosition)
                        //now background will be set to dark outline
                        notifyItemChanged(holder.absoluteAdapterPosition)
                    }
                }
                // 2. there is no possible match - update backgrounds to show newly selected number
                else {
                    //save old index
                    val previousAdapterPosition = previouslyClickedNumber.adapterPosition
                    //update index to current number
                    previouslyClickedNumber = thisNumber
                    //now background will be reset to pink
                    notifyItemChanged(previousAdapterPosition)
                    //now background will be set to dark outline
                    notifyItemChanged(holder.absoluteAdapterPosition)
                }
                Log.e(
                    "xyz",
                    "\nid: ${thisNumber.id}," +
                            "\nright: ${thisNumber.rightNeighbour} " +
                            "\nleft: ${thisNumber.leftNeighbour} " +
                            "\ntop: ${thisNumber.topNeighbour} " +
                            "\nbottom: ${thisNumber.bottomNeighbour}"
                )
            }
            // if hint has been requested
            if (hint1PositionInAdapter != null) {
                //user just clicked so I count it
                numberOfClicksAfterHint++
                // if user clicked twice I should hide hint again
                if (numberOfClicksAfterHint >= 2) {
                    hideHint()
                }
            }
        }
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
            listener.updateNumber(it)
        }
        //left - update and send data to viewModel
        leftNeighbour?.let {
            it.rightNeighbour = removedNumber.rightNeighbour
            listener.updateNumber(it)
        }
        //top - update and send data to viewModel
        topNeighbour?.let {
            it.bottomNeighbour = removedNumber.bottomNeighbour
            listener.updateNumber(it)
        }
        //bottom - update and send data to viewModel
        bottomNeighbour?.let {
            it.topNeighbour = removedNumber.topNeighbour
            listener.updateNumber(it)
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
                listener.removeNumber(indexOfFirstInLine)
            }
            notifyDataSetChanged()
        }
        //update current number in view model if not removed
        else {
            //if number stays in the game = show it as white
            //remove neighbours from removed number just to make sure
            removedNumber.rightNeighbour = -1
            removedNumber.leftNeighbour = -1
            removedNumber.topNeighbour = -1
            removedNumber.bottomNeighbour = -1
            listener.updateNumber(removedNumber)
            notifyItemChanged(removedNumber.adapterPosition)
        }

    }

    private fun hideHint() {
        hint1PositionInAdapter?.let { notifyItemChanged(it) }
        hint2PositionInAdapter?.let { notifyItemChanged(it) }
        hint1PositionInAdapter = null
        hint2PositionInAdapter = null
        numberOfClicksAfterHint = 0

    }

    fun notifyDataSetChanged(numbersArray: ArrayList<NumberItem>) {
        this.numbersArray.clear()
        this.numbersArray.addAll(numbersArray)
        notifyDataSetChanged()
    }

    fun showHint() {
        numberOfClicksAfterHint = 0
        var isThereMatch = false
        //subarray to avoid matches with invisible numbers
        val matchSubArray: ArrayList<NumberItem> = arrayListOf<NumberItem>()
        matchSubArray.addAll(numbersArray.subList(0, itemCount))
        for (index in 0 until matchSubArray.size) {
            val numberItem = matchSubArray[index]
            if (numberItem.isNumberStillInGame) {
                val rightNeighbour = matchSubArray.getById(numberItem.rightNeighbour)
                if (rightNeighbour != null &&
                    (rightNeighbour.numberValue == numberItem.numberValue
                            || (rightNeighbour.numberValue?.plus(numberItem.numberValue) == 10)
                            )
                ) {
                    hint1PositionInAdapter = numberItem.adapterPosition
                    hint2PositionInAdapter = rightNeighbour.adapterPosition
                    isThereMatch = true
                    break
                }
                val bottomNeighbour = matchSubArray.getById(numberItem.bottomNeighbour)
                if (bottomNeighbour != null &&
                    (bottomNeighbour.numberValue == numberItem.numberValue
                            || (bottomNeighbour.numberValue?.plus(numberItem.numberValue) == 10)
                            )
                ) {
                    hint1PositionInAdapter = numberItem.adapterPosition
                    hint2PositionInAdapter = bottomNeighbour.adapterPosition
                    isThereMatch = true
                    break
                }
            }
        }
        if (isThereMatch) {
            hint1PositionInAdapter?.let {
                notifyItemChanged(it)
                listener.scrollToPosition(it)
            }
            hint2PositionInAdapter?.let { notifyItemChanged(it) }

        } else {
            Toast.makeText(context, "No more matches!", Toast.LENGTH_SHORT).show()
        }
    }

}