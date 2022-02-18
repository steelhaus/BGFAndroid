package com.example.boardgamefinder.core

import android.app.Activity
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler

object ExceptionHandler {
    fun getExceptionHandler(activity: Activity) =
        CoroutineExceptionHandler { _, _ ->
        // activity.runOnUiThread - cannot make toast from worker thread in other way
            activity.runOnUiThread {
                Toast.makeText(activity.applicationContext, "Something went wrong. Please, check the Internet connection.", Toast.LENGTH_LONG).show()
            }
        }
}