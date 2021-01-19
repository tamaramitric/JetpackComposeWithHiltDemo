package com.example.jetpackcomposelearning.loginscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun onEmailChanged(newEmail: String){
        email.postValue(newEmail)
    }

    fun onPasswordChanged(newPassword: String){
        password.postValue(newPassword)
    }
}