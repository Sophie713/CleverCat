package com.example.clevercat.activityMain.useCases

import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.room.repository.NumberItemsRepository
import javax.inject.Inject

class GetLastNumberUseCase @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {
    fun getLastNumber(): NumberItem {
        return numberItemsRepository.getLast()
    }
}