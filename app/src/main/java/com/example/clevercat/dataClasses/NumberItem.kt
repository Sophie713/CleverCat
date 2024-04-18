package com.example.clevercat.dataClasses

data class NumberItem(
    val id: Int,
    val numberValue: Int,
    var leftNeighbour: Int,
    var rightNeighbour: Int,
    var topNeighbour: Int,
    var bottomNeighbour: Int,
) {
    var isHint = false
    var isNumberStillInGame = true
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
