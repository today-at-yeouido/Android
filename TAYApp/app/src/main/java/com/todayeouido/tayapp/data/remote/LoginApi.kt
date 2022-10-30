package com.todayeouido.tayapp.data.remote

import androidx.room.Delete
import com.todayeouido.tayapp.data.remote.dto.login.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("user/{sns}/login/manage/")
    suspend fun postSocialLogin(
        @Path("sns") sns: String,
        @Body snsLogin: SnsLoginDto
    ): Response<LoginResponse>

    @DELETE(Constants.DELETE_USER_INFO)
    suspend fun deleteUserInfo() : Response<Void>
}

