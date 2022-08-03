package com.example.tayapp.data.pref.model


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
