package com.example.tayapp.data.remote.dto.login

import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.presentation.states.UserInfo
import com.google.gson.annotations.SerializedName


data class LoginDto(
    val email: String,
    val password: String
)

data class SnsLoginDto(
    @SerializedName("access_token")
    val access: String
)

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    val user: User
) {
    data class User(
        val pk: String,
        val email: String
    )
}

fun LoginResponse.toPref(): UserPref = UserPref(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    id = this.user.pk,
    email = this.user.email,
)

fun LoginResponse.toState(): UserInfo =
    UserInfo(id = this.user.pk, email = this.user.email, refreshToken = this.refreshToken)
