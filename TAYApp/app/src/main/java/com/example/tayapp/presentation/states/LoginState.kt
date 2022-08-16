package com.example.tayapp.presentation.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LoginState {
    var user by mutableStateOf(UserInfo())

    fun isLogin(): Boolean {
        return user.id != ""
    }
}

data class UserInfo(
    val id: String = "",
    val email: String = "",
    val refreshToken: String = ""
)