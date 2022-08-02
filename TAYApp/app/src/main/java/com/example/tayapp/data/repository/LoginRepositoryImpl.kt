package com.example.tayapp.data.repository

import com.example.tayapp.data.pref.LoginPref
import com.example.tayapp.data.pref.model.UserData
import com.example.tayapp.data.pref.model.toUser
import com.example.tayapp.data.remote.LoginApi
import com.example.tayapp.data.remote.dto.*
import com.example.tayapp.domain.repository.LoginRepository
import com.example.tayapp.domain.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val pref: LoginPref,
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun getUser(): UserData =
        pref.getUser().first()


    override suspend fun setLoginUser(user: UserData) {
        pref.setUser(user)
    }

    override suspend fun requestRegistration(registrationDto: RegistrationDto) {
        loginApi.postRegistration(registrationDto)
    }

    override suspend fun requestLogin(loginDto: LoginDto): LoginResponse {
        return loginApi.postLogin(loginDto)
    }

    override suspend fun requestLogout(token: String): LogoutResponse {
        return loginApi.postLogout(token)
    }

    override suspend fun requestRefreshToken(token: String): JwtRefreshResponse {
        return loginApi.postJwtRefresh(token)
    }
}