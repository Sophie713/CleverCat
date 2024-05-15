package com.example.clevercat.database.useCases

import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.database.repository.NumberItemsRepository
import javax.inject.Inject

class GetLastNumberUseCase @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {
    fun getLastNumber(): NumberItem? {
        return numberItemsRepository.getLast()
    }
}