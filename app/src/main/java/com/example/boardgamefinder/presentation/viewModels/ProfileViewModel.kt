package com.example.boardgamefinder.presentation.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.boardgamefinder.core.ServerExceptionConverter
import com.example.boardgamefinder.data.retrofit.UserRepository
import com.example.boardgamefinder.domain.usecase.GetBreedsUseCase
import kotlinx.coroutines.*
import java.lang.Exception

internal class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val _countBreeds = MutableLiveData<String>()
    val countBreeds: LiveData<String> = _countBreeds

    fun getBreeds(){
        val useCase = GetBreedsUseCase(UserRepository())
        viewModelScope.launch {
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
