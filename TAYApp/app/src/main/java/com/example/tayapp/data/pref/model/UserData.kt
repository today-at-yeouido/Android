package com.example.tayapp.data.pref.model

import com.example.tayapp.domain.model.User

class UserData(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val email: String,
    val name: String,
)

fun UserData.toUser(): User = User(accessToken, id, email, name)