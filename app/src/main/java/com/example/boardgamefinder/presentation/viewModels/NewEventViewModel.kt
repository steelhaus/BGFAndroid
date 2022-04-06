package com.example.boardgamefinder.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

internal class NewEventViewModel(app: Application) : AndroidViewModel(app) {
    private val _resend = MutableLiveData<Boolean>()
    val resend: LiveData<Boolean> = _resend

    fun validateCoordinates(coordinates: String): Boolean {
        val decimalPattern = "([0-9]*)\\.([0-9]*)"
        val coos = coordinates.split(", ").toTypedArray()
        return coos.size == 2 &&
                Pattern.matches(decimalPattern, coos[0]) &&
                Pattern.matches(decimalPattern, coos[1]) &&
                coos[0].toDouble() <= 90 &&
                coos[0].toDouble() >= -90 &&
                coos[1].toDouble() <= 180 &&
                coos[1].toDouble() >= -180
    }
}