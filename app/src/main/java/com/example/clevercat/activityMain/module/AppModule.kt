package com.example.clevercat.activityMain.module

import android.content.Context
import androidx.room.Room
import com.example.clevercat.activityMain.useCases.DeleteSavedGameUseCase
import com.example.clevercat.activityMain.useCases.GetGameNumbersUseCase
import com.example.clevercat.activityMain.useCases.GetLastNumberUseCase
import com.example.clevercat.activityMain.useCases.GetSavedGameNumbersCount
import com.example.clevercat.activityMain.useCases.SaveGameUseCase
import com.example.clevercat.room.CleverCatDatabase
import com.example.clevercat.room.repository.NumberItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCleverCatDatabase(@ApplicationContext context: Context): CleverCatDatabase {
        return Room.databaseBuilder(
            context,
            CleverCatDatabase::class.java,
            CleverCatDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesNumberItemsRepository(
        numberItemsDatabase: CleverCatDatabase
    ): NumberItemsRepository {
        return NumberItemsRepository(numberItemsDatabase.numberItemsDao())
    }


    @Provides
    @Singleton
    fun providesDeleteSavedGameUseCase(numberItemsRepository: NumberItemsRepository):DeleteSavedGameUseCase {
        return DeleteSavedGameUseCase(numberItemsRepository)
    }

    @Provides
    @Singleton
    fun providesGetGameNumbersUseCase(numberItemsRepository: NumberItemsRepository):GetGameNumbersUseCase {
        return GetGameNumbersUseCase(numberItemsRepository)
    }

    @Provides
    @Singleton
    fun providesGetLastNumberUseCase(numberItemsRepository: NumberItemsRepository):GetLastNumberUseCase {
        return GetLastNumberUseCase(numberItemsRepository)
    }

    @Provides
    @Singleton
    fun providesSaveGameUseCase(numberItemsRepository: NumberItemsRepository):SaveGameUseCase {
        return SaveGameUseCase(numberItemsRepository)
    }

    @Provides
    @Singleton
    fun providesGetSavedGameNumbersCount(numberItemsRepository: NumberItemsRepository): GetSavedGameNumbersCount{
        return GetSavedGameNumbersCount(numberItemsRepository)
    }



}