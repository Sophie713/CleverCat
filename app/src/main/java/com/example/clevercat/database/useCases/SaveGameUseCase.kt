package com.example.clevercat.database.useCases

import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.database.repository.NumberItemsRepository
import javax.inject.Inject

class SaveGameUseCase  @Inject constructor(private val numberItemsRepository: NumberItemsRepository)  {

    fun saveGame(numbersList: List<NumberItem>) {
        numberItemsRepository.upsertItems(numbersList)
    }
}