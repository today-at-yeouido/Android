package com.example.tayapp.data.remote.dto.login

data class RefreshTokenDto(
    val access : String
)

data class JwtRefreshResponse(
    val access : String
)