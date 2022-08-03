package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.dto.bill.LoginDto
import com.example.tayapp.data.remote.dto.bill.LoginResponse
import com.example.tayapp.data.remote.dto.login.JwtRefreshResponse
import com.example.tayapp.data.remote.dto.login.LogoutResponse
import com.example.tayapp.data.remote.dto.login.RefreshTokenDto
import com.example.tayapp.data.remote.dto.login.RegistrationDto
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
        @Body token: String
    ): LogoutResponse

    @POST(Constants.POST_JWT_REFRESH)
    suspend fun postJwtRefresh(
        @Body token : RefreshTokenDto
    ): Response<JwtRefreshResponse>
}

