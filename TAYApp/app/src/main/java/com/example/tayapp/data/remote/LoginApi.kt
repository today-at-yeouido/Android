package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(Constants.POST_REGISTRATION)
    fun postRegistration(
        @Body registration: RegistrationDto
    )

    @POST(Constants.POST_LOGIN)
    fun postLogin(
        @Body loginDto: LoginDto
    ): LoginResponse

    @POST(Constants.POST_LOGOUT)
    fun postLogout(
        @Body token: String
    ): LogoutResponse

    @POST(Constants.POST_JWT_REFRESH)
    fun postJwtRefresh(
        @Body token: String
    ): JwtRefreshResponse

}