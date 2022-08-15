package com.example.tayapp.data.remote.dto.login

data class RefreshTokenDto(
    val refresh : String
)

data class JwtRefreshResponse(
    val access : String
)