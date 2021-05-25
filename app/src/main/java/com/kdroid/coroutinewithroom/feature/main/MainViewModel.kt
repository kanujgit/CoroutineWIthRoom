package com.kdroid.coroutinewithroom.feature.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kdroid.coroutinewithroom.data.LoginState
import com.kdroid.coroutinewithroom.data.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao() }
    val userDeleted = MutableLiveData<Boolean>()
    val signOut = MutableLiveData<Boolean>()

    fun onSignOut() {
        LoginState.logout()
        signOut.value = true
    }

    fun onDeleteUser() {
        coroutineScope.launch {
            LoginState.user?.let { user ->
                db.deleteUser(user.id)
            }
            withContext(Dispatchers.Main) {
                LoginState.logout()
                signOut.value = true
            }
        }
    }

}