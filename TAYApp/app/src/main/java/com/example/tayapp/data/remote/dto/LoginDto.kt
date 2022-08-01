package com.example.tayapp.data.remote.dto



data class LoginDto (
    val email : String,
    val password : String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken:String,
    val user : User
){
    data class User(
        val pk : String,
        val email : String
    )
}

