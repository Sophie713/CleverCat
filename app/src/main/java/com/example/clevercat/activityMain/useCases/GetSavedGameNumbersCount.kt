package com.example.clevercat.activityMain.useCases

import com.example.clevercat.room.repository.NumberItemsRepository
import javax.inject.Inject

class GetSavedGameNumbersCount @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {

    fun getNumbersCount(): Int {
        return numberItemsRepository.getNumbersCount()
    }
}