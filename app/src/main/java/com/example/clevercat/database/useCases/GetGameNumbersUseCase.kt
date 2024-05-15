package com.example.clevercat.database.useCases

import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.database.repository.NumberItemsRepository
import javax.inject.Inject

class GetGameNumbersUseCase @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {
    fun getAllNumbers(): List<NumberItem> {
        return numberItemsRepository.getAllNumbers()
    }
}