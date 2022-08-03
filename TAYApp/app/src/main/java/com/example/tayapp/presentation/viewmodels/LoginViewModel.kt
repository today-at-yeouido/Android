package com.example.tayapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tayapp.data.remote.dto.LoginDto
import com.example.tayapp.data.remote.dto.RegistrationDto
import com.example.tayapp.domain.use_case.LoginUseCases
import com.example.tayapp.presentation.states.LoginUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
) : ViewModel() {

    init {
        checkLogin()
    }

    var user by mutableStateOf(LoginUserUiState())
        private set

    var isLogin by mutableStateOf(false)
        private set

    fun requestLogin(e: String, p1: String) {
        viewModelScope.launch {
            user = LoginUserUiState(e, p1)
            loginUseCases.requestLoginUseCase(
                LoginDto(
                    user.email,
                    user.password1
                )
            )
        }
    }

    fun requestRegister(e: String, p1: String, p2: String, nav: () -> Unit) {
        viewModelScope.launch {
            val res = loginUseCases.requestRegisterUseCase(
                RegistrationDto(email = e, password1 = p1, password2 = p2)
            )
            if(res) {
                nav()
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            loginUseCases.getUserUseCase()
        }
    }

    private fun checkLogin() =
        viewModelScope.launch {
            isLogin = loginUseCases.checkLoginUseCase()
        }
}