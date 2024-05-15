package com.example.clevercat.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
class CleverCatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}