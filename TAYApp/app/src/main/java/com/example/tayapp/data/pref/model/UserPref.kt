package com.example.tayapp.data.pref.model

import com.example.tayapp.presentation.states.UserInfo


class UserPref(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val email: String,
) {
    override fun toString(): String {
        return "accessToken: $accessToken, refreshToken: $refreshToken id : $id"
    }
}

fun UserPref.toState(): UserInfo =
    UserInfo(id = this.id, email = this.email, refreshToken = this.refreshToken)
