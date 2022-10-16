package com.todayeouido.tayapp.presentation.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.todayeouido.tayapp.utils.ThemeConstants.SYSTEM

object UserState {
    var user by mutableStateOf(UserInfo())

    var mode by mutableStateOf(SYSTEM)

    var network by mutableStateOf(true)
    fun isLogin(): Boolean {
        return user.id != "Guest"
    }
}

data class UserInfo(
    val id: String = "Guest",
    val email: String = "",
    val refreshToken: String = "",
    val sns: String = ""
)
