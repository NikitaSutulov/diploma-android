package com.nikitasutulov.macsro.utils

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun handleError(view: View, message: String) {
    Log.e("API Error", message)
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}
