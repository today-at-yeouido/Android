package com.example.tayapp.utils

class UnAuthorizationError() : Exception() {
    override fun getLocalizedMessage(): String {
        return "401 Error, unAuthorization token"
    }
}