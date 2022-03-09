package com.example.boardgamefinder.core

import android.content.Context
import android.provider.Settings.Global.getString
import com.example.boardgamefinder.data.retrofit.DataException
import java.lang.Exception

/**
 * Converts custom data exception into a message for user
 */
object DataExceptionConverter {
    fun convertServerExceptionToText(c: Context, exception: Exception): String{
        return when(exception){
            is DataException.InternetException -> c.getString(R.string.InternetException)
            is DataException.Response500 -> c.getString(R.string.Response500)
            is DataException.UnknownServerError -> c.getString(R.string.UnknownServerError)
            else -> c.getString(R.string.AnotherError)
        }
    }
}