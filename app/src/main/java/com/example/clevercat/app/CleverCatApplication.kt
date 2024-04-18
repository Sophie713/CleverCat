package com.example.clevercat.app

import android.app.Application
import com.sophie.miller.clevercat.sharedPreferences.SharedPrefs
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

val prefs: SharedPrefs by lazy {
    CleverCatApplication.prefs!!
}

@HiltAndroidApp
class CleverCatApplication : Application() {
    companion object {
        var prefs: SharedPrefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPrefs(applicationContext)
    }
}