package com.example.boardgamefinder.presentation.viewModels

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.boardgamefinder.core.ExceptionHandler
import com.example.boardgamefinder.core.ServerExceptionConverter
import com.example.boardgamefinder.data.retrofit.UserRepository
import com.example.boardgamefinder.domain.usecase.GetBreedsUsecase
import kotlinx.coroutines.*
import java.lang.Exception

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val _countBreeds = MutableLiveData<String>()
    val countBreeds: LiveData<String> = _countBreeds

    fun getBreeds(activity: Activity){
        // TODO change scope
        val useCase = GetBreedsUsecase(UserRepository())
        CoroutineScope(Dispatchers.IO + ExceptionHandler.getExceptionHandler(activity)).launch {
            val result = useCase.execute()

            withContext(Dispatchers.Main) {
                if (result.isSuccess)
                    _countBreeds.value = result.getOrNull()!!.size.toString()
                else
                    Toast.makeText(getApplication(), ServerExceptionConverter.convertServerExceptionToText(result.exceptionOrNull()!! as Exception), Toast.LENGTH_LONG).show()
            }
        }
    }
}