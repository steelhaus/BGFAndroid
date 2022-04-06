package com.example.boardgamefinder.presentation.viewModels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.boardgamefinder.R
import com.example.boardgamefinder.core.MySettings
import com.example.boardgamefinder.core.toMessage
import com.example.boardgamefinder.data.retrofit.DataException
import com.example.boardgamefinder.data.retrofit.UserRepository
import com.example.boardgamefinder.domain.models.*
import com.example.boardgamefinder.domain.usecase.ConfirmCodeUseCase
import com.example.boardgamefinder.domain.usecase.GetBreedsUseCase
import com.example.boardgamefinder.domain.usecase.GetEventsUseCase
import com.example.boardgamefinder.domain.usecase.RegisterUseCase
import com.example.boardgamefinder.presentation.views.WelcomeActivity
import com.example.boardgamefinder.utils.CredentialsValidatorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

internal class ConfirmationCodeViewModel(app: Application) : AndroidViewModel(app) {
    private val _resend = MutableLiveData<Boolean>()
    val resend: LiveData<Boolean> = _resend

    private var resendTimer: Timer = Timer()

    private lateinit var mSettings: SharedPreferences

    private val _confirmed = MutableLiveData<Boolean>()
    val confirmed: LiveData<Boolean> = _confirmed

    init {
        setTimer()
    }

    /**
     * Sets timer on 10 seconds
     */
    private fun setTimer()
    {
        _resend.value = false
        resendTimer.schedule(object : TimerTask() {
            override fun run() {
                    _resend.value = true
            }
        }, 10000)
    }

    fun resendCode(){
        if(resend.value != true)
            return

        // ToDo ResendCodeUseCase

        setTimer()
    }

    fun confirm(code: String){
        mSettings = (getApplication() as Context).getSharedPreferences(MySettings.APP_PREFERENCES, AppCompatActivity.MODE_PRIVATE)

        val jwt = mSettings.getString(MySettings.APP_PREFERENCES_TOKEN, "") ?: ""

        val useCase = ConfirmCodeUseCase(UserRepository())
        viewModelScope.launch {
            val result = useCase.execute(code, jwt)

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    confirmationSuccess(result.getOrNull()!!)
                }
                // ToDo handle exceptions
                else if(result.exceptionOrNull() != null) {
                    Toast.makeText(getApplication(), (result.exceptionOrNull() as Exception).toMessage(getApplication()), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun confirmationSuccess(tokens: Tokens){
        val editor = mSettings.edit()
        editor.putString(MySettings.APP_PREFERENCES_TOKEN, tokens.accessToken)
        editor.putString(MySettings.APP_PREFERENCES_REFRESH_TOKEN, tokens.refreshToken)
        editor.putBoolean(MySettings.APP_PREFERENCES_LOGGED_IN, true)
        editor.putBoolean(MySettings.APP_PREFERENCES_CODE_STAGE, false)
        editor.apply()
    }
}
