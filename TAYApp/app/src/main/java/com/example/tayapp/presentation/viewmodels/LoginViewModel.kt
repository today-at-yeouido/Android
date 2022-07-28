package com.example.tayapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.data.repository.LoginRepository
import com.example.tayapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val pref: LoginRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            user = pref.getLoginUser()
        }
    }

    private lateinit var user: User

    fun isLogin(): Boolean = !user.isGuest()

    suspend fun getLogin(): User {
        return withContext(viewModelScope.coroutineContext) { pref.getLoginUser() }
    }

    fun setLogin(user: User) {
        viewModelScope.launch {
            pref.setLoginUser(user)
        }
    }
}