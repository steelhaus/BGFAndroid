package com.example.boardgamefinder.presentation.views.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.boardgamefinder.databinding.ActivityLogInBinding
import com.example.boardgamefinder.domain.models.Email
import com.example.boardgamefinder.domain.models.Password
import com.example.boardgamefinder.presentation.viewModels.LogInViewModel

internal class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private val logInViewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {openActivity(SignUpActivity::class.java)}

        binding.loginButton.setOnClickListener { logInViewModel.login(
            Email(binding.editEmail.text.toString()),
            Password(binding.editPassword.text.toString())
        ) }

        logInViewModel.success.observe(this){
            if(logInViewModel.success.value == true)
                openActivity(MainActivity::class.java)
            else{
                //ToDo highlight fields
            }
        }
    }

    private fun openActivity(activity: Class<out Activity>){
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}