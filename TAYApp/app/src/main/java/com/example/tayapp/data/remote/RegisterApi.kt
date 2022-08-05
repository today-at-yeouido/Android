package com.example.tayapp.data.remote

import retrofit2.http.POST

interface RegisterApi {

    @POST
    fun postRegistration()

}