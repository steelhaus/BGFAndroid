package com.example.boardgamefinder.core

import com.example.boardgamefinder.data.retrofit.exceptions.Response500
import com.example.boardgamefinder.data.retrofit.exceptions.UnknownServerError
import java.lang.Exception

object ServerExceptionConverter {
    fun convertServerExceptionToText(exception: Exception): String{
        return when(exception){
            is Response500 -> "Server is broken"
            is UnknownServerError -> "Something went wrong"
            else -> "Error"
        }
    }
}