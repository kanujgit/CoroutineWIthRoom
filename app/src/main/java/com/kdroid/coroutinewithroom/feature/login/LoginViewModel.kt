package com.kdroid.coroutinewithroom.feature.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kdroid.coroutinewithroom.data.LoginState
import com.kdroid.coroutinewithroom.data.User
import com.kdroid.coroutinewithroom.data.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao() }

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    fun login(username: String, password: String) {
        coroutineScope.launch {
            val user: User = db.getUser(username)
            if (user != null) {
                if (user.passwordHash == password.hashCode()) {
                    LoginState.login(user)
                    LoginState.isLoggedIn = true
                    withContext(Dispatchers.Main) {
                        loginComplete.value = true
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        error.value = "Password is not matched"
                    }
                }

            } else {
                withContext(Dispatchers.Main) {
                    error.value = "User detail not found"
                }

            }
        }

    }
}