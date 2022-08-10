package com.example.tayapp.data.repository

import android.util.Log
import com.example.tayapp.data.pref.PrefDataSource
import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.data.remote.LoginApi
import com.example.tayapp.data.remote.dto.bill.LoginDto
import com.example.tayapp.data.remote.dto.bill.LoginResponse
import com.example.tayapp.data.remote.dto.login.JwtRefreshResponse
import com.example.tayapp.data.remote.dto.login.LogoutResponse
import com.example.tayapp.data.remote.dto.login.RefreshTokenDto
import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val pref: PrefDataSource,
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun getUser(): UserPref {
        return pref.getUser().first()
    }

    override suspend fun setLoginUser(user: UserPref) {
        pref.setUser(user)
    }

    override suspend fun getRefreshToken(): String {
        return pref.getRefreshToken().first()
    }

    override suspend fun updateAccessToken(accesToken: String){
        pref.updateAccessToken(accesToken)
    }

    override suspend fun requestRegistration(registrationDto: RegistrationDto): Response<Void> {
       return loginApi.postRegistration(registrationDto)
    }

    override suspend fun requestLogin(loginDto: LoginDto): Response<LoginResponse> {
        return loginApi.postLogin(loginDto)
    }

    override suspend fun requestLogout(token: String): LogoutResponse {
        return loginApi.postLogout(token)
    }

    override suspend fun requestRefreshToken(token: RefreshTokenDto): Response<JwtRefreshResponse> {
        return loginApi.postJwtRefresh(token)
    }

}