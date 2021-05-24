package com.kdroid.coroutinewithroom.feature.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    fun login(username: String, password: String) {

    }
}