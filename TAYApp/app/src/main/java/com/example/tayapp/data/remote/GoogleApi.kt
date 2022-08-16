package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.dto.LoginGoogleRequest
import com.example.tayapp.data.remote.dto.LoginGoogleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleApi {
    @POST("oauth2/v4/token")
    suspend fun fetchGoogleAuthInfo(@Body request: LoginGoogleRequest): Response<LoginGoogleResponse>?
}