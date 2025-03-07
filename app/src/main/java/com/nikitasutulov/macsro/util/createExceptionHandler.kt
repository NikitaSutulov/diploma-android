package com.nikitasutulov.macsro.util

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

fun createExceptionHandler(viewModel: ViewModel): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        Log.e(viewModel::class.java.simpleName, "Exception handled: ${throwable.stackTraceToString()}")
    }
}