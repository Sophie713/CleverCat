package com.example.clevercat.dataClasses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.clevercat.sharedClasses.constants.Constants

@Entity(tableName = Constants.NUMBERS_TABLE_NAME)
data class NumberItem(
    @PrimaryKey
    val id: Int,
    val numberValue: Int,
    var leftNeighbour: Int,
    var rightNeighbour: Int,
    var topNeighbour: Int,
    var bottomNeighbour: Int,
) {
    var isNumberStillInGame = true

    @Ignore
    var isHint = false
    @Ignore
    var isSelected = false

    override fun toString(): String {
        return "id: $id" +
                "\nnumberValue: $numberValue" +
                "\nleftNeighbour: $leftNeighbour" +
                "\nrightNeighbour: $rightNeighbour" +
                "\ntopNeighbour: $topNeighbour" +
                "\nbottomNeighbour: $bottomNeighbour" +
                "\nisHint: $isHint" +
                "\nisNumberStillInGame: $isNumberStillInGame"
    }

    fun clearState() {
        isHint = false
        isSelected = false
    }
}
