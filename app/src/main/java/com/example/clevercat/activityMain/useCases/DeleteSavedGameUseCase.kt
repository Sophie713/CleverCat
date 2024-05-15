package com.example.clevercat.activityMain.useCases

import com.example.clevercat.room.repository.NumberItemsRepository
import javax.inject.Inject

class DeleteSavedGameUseCase @Inject constructor(private val numberItemsRepository: NumberItemsRepository) {
    fun deleteCurrentlySavedGame() {
        return numberItemsRepository.deleteAllNumbers()
    }
}