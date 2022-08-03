package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Named

interface LoginApi {

    @POST(Constants.POST_REGISTRATION)
    suspend fun postRegistration(
        @Body registration: RegistrationDto
    ): Response<Void>

    @POST(Constants.POST_LOGIN)
    suspend fun postLogin(
        @Body loginDto: LoginDto
    ): Response<LoginResponse>

    @POST(Constants.POST_LOGOUT)
    fun postLogout(
        @Body token: String
    ): LogoutResponse

    @POST(Constants.POST_JWT_REFRESH)
    suspend fun postJwtRefresh(
        @Body token : RefreshTokenDto
    ): Response<JwtRefreshResponse>
}

