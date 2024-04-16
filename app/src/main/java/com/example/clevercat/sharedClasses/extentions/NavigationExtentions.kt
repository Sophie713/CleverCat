package com.example.clevercat.sharedClasses.extentions

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.clevercat.sharedClasses.constants.Constants.LOG_TAG

/**
Method used to navigate safely with SafeArgs.
It is required because when you trigger the navigation event twice in a row it would crash
as it thinks that you are navigating from a different destination.
 */
fun NavController.safeNavigate(
    directions: NavDirections,
    navOptions: NavOptions? = null
) {
    try {
        navigate(directions, navOptions)
    } catch (exception: Exception) {
       //todo log to crashlytics
        Log.e(LOG_TAG, "navigate crash: ${exception.message}")
    }
}

fun NavController.safeNavigate(
    @IdRes destination: Int,
    arguments: Bundle? = null,
    navOptions: NavOptions? = null
) {
    try {
        navigate(destination, arguments, navOptions)
    } catch (exception: Exception) {
        //todo log to crashlytics
        Log.e(LOG_TAG, "navigate crash: ${exception.message}")
    }
}