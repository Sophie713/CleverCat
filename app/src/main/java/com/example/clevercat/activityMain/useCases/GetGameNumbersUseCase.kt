package com.example.clevercat.activityMain.useCases

import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.room.repository.NumberItemsRepository
import javax.inject.Inject

class GetGameNumbersUseCase @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {
    fun getAllNumbers(): List<NumberItem> {
        return numberItemsRepository.getAllNumbers()
    }
}