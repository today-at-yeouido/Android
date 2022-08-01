package com.example.tayapp.data.remote.dto

data class RefreshDto(
    val refresh : String
)

data class LogoutResponse(
    val detail: String
)

data class JwtRefreshResponse(
    val access : String
)