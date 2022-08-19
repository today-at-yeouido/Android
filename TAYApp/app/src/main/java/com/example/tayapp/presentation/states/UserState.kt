package com.example.tayapp.presentation.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tayapp.presentation.utils.ThemeModeConst.SYSTEM

object UserState {
    var user by mutableStateOf(UserInfo())

    var mode by mutableStateOf(SYSTEM)

    var network by mutableStateOf(true)
    fun isLogin(): Boolean {
        return user.id != ""
    }
}

data class UserInfo(
    val id: String = "",
    val email: String = "",
    val refreshToken: String = "",
    val sns: String = ""
)
