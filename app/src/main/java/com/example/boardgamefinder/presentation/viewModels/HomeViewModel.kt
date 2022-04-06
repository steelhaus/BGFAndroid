package com.example.boardgamefinder.presentation.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.boardgamefinder.core.toMessage
import com.example.boardgamefinder.data.retrofit.UserRepository
import com.example.boardgamefinder.domain.models.Event
import com.example.boardgamefinder.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    init {
        getEvents()
    }

    fun getEvents(){
        val useCase = GetEventsUseCase(UserRepository())
        viewModelScope.launch {
            val result = useCase.execute()

            withContext(Dispatchers.Main) {
                if (result.isSuccess)
                    _events.value = result.getOrNull()
                else if(result.exceptionOrNull() != null)
                    Toast.makeText(getApplication(), (result.exceptionOrNull() as Exception).toMessage(getApplication()), Toast.LENGTH_LONG).show()
            }
        }
    }
}
