package com.example.tayapp.data.remote

object Constants {
    const val BASE_URL = "http://192.168.0.15:8000"
    const val AUTHORIZATION = "Authorization"

    const val POST_REGISTRATION = "/account/registration"
    const val POST_LOGIN = "/account/login"
    const val POST_LOGOUT = "/account/logout"
    const val POST_JWT_REFRESH = "/account/token/refresh"
}
