package com.example.tayapp.presentation.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LoginState {
    var loginState by mutableStateOf(false)
        private set

    fun changeState() {
        loginState = !loginState
    }
}