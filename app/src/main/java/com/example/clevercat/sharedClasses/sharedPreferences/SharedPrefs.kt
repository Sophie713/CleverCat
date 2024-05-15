package com.sophie.miller.clevercat.sharedPreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class SharedPrefs(context: Context) {
    val SHARED_PREFS_FILENAME = "com.sophie.miller.clevercat.preferences"
    val LATEST_ID = "LATEST_ID"
    val prefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_FILENAME, MODE_PRIVATE);

    /**
     * we save the latest ID to avoid slow operations
     * in case of error or empty value we get -1
     */
    var latestId: Int?
        get() = prefs.getInt(LATEST_ID, -1)
        set(value) = prefs.edit().putInt(LATEST_ID, value ?: -1).apply()
}