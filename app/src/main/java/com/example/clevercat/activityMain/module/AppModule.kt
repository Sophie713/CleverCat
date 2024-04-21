package com.example.clevercat.activityMain.module

import android.content.Context
import androidx.room.Room
import com.example.clevercat.activityMain.MainActivity
import com.example.clevercat.activityMain.viewModels.GameFragmentViewModel
import com.example.clevercat.app.CleverCatApplication
import com.example.clevercat.room.CleverCatDatabase
import com.example.clevercat.room.NumberItemsDao
import com.example.clevercat.room.repository.NumberItemsRepository
import com.example.clevercat.sharedClasses.abstractClasses.BaseFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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
        numberItemsDao: CleverCatDatabase
    ): NumberItemsRepository {
        return NumberItemsRepository(numberItemsDao.numberItemsDao())
    }



}