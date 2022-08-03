package com.example.tayapp.data.repository

import android.util.Log
import com.example.tayapp.data.pref.LoginPref
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
    private val pref: LoginPref,
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun getUser(): UserPref {
        val g = pref.getUser().first()
        Log.d("##99", "pref ${g.accessToken}")
       return g
    }


    override suspend fun setLoginUser(user: UserPref) {
        pref.setUser(user)
    }

    override suspend fun getRefreshToken(): String {
        val p = pref.getRefreshToken().first()
        Log.d("##99", "getRefresh ${p.toString()}")
        return p
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