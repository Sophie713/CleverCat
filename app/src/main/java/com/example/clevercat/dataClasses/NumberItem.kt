package com.example.clevercat.dataClasses

data class NumberItem(
    val id: Int,
    val numberValue: Int,
    var leftNeighbour: Int,
    var rightNeighbour: Int,
    var topNeighbour: Int,
    var bottomNeighbour: Int,
) {
    var adapterPosition: Int = 0
    var isNumberStillInGame = true
}
