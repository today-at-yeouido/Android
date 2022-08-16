package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.dto.login.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


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
    suspend fun postLogout(
        @Body token: RefreshTokenDto
    ): Response<LogoutResponse>

    @POST(Constants.POST_JWT_REFRESH)
    suspend fun postJwtRefresh(
        @Body token: RefreshTokenDto
    ): Response<JwtRefreshResponse>

    @POST(Constants.POST_SOCIAL_LOGIN)
    suspend fun postSocialLogin(
        @Body snsLogin: SnsLoginDto
    ): Response<LoginResponse>
}

