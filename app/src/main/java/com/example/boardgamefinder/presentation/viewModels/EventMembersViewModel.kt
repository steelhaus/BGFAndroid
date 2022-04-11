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
import com.example.boardgamefinder.domain.models.User
import com.example.boardgamefinder.domain.usecase.GetEventMembersUseCase
import com.example.boardgamefinder.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class EventMembersViewModel(app: Application) : AndroidViewModel(app) {

    private val _members = MutableLiveData<List<User>>()
    val members: LiveData<List<User>> = _members

    fun getMembers(eventId: Int){
        val useCase = GetEventMembersUseCase(UserRepository())
        viewModelScope.launch {
            val result = useCase.execute(eventId)

            withContext(Dispatchers.Main) {
                if (result.isSuccess)
                    _members.value = result.getOrNull()
                else if(result.exceptionOrNull() != null)
                    Toast.makeText(getApplication(), (result.exceptionOrNull() as Exception).toMessage(getApplication()), Toast.LENGTH_LONG).show()
            }
        }
    }
}
