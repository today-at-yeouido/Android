package com.example.tayapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tayapp.domain.repository.LoginRepository
import com.example.tayapp.domain.model.User
import com.example.tayapp.domain.use_case.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ViewModel() {

    init {
        viewModelScope.launch {
            user = loginUseCases.getUserUseCase()
        }
    }

    private lateinit var user: User

    fun isLogin(): Boolean = !user.isGuest()


    fun setLogin(user: User, p: String) {
        viewModelScope.launch {
            loginUseCases.setLoginUseCase(user, p)
        }
    }
}