package com.example.clevercat.sharedClasses.errorHandling

import android.util.Log
import com.example.clevercat.sharedClasses.constants.Constants.LOG_TAG
import kotlinx.coroutines.CoroutineExceptionHandler

fun DefaultCoroutineExceptionHandler(whereFrom: String, doAfterHandle: ((Throwable) -> Unit)?) = CoroutineExceptionHandler { _, exception ->
    //todo log to Crashlytics
    Log.e(LOG_TAG, "$whereFrom, Coroutine Exception: ${exception}")
    doAfterHandle?.invoke(exception)
}