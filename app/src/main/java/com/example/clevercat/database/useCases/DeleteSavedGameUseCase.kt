package com.example.clevercat.database.useCases

import com.example.clevercat.database.repository.NumberItemsRepository
import javax.inject.Inject

class DeleteSavedGameUseCase @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {
    fun deleteCurrentlySavedGame() {
        return numberItemsRepository.deleteAllNumbers()
    }
}