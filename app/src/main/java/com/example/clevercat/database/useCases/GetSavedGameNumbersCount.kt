package com.example.clevercat.database.useCases

import com.example.clevercat.database.repository.NumberItemsRepository
import javax.inject.Inject

class GetSavedGameNumbersCount @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {

    fun getNumbersCount(): Int {
        return numberItemsRepository.getNumbersCount()
    }
}