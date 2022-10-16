package com.todayeouido.tayapp.domain.repository


import com.todayeouido.tayapp.data.pref.model.UserPref
import com.todayeouido.tayapp.data.remote.dto.LoginGoogleRequest
import com.todayeouido.tayapp.data.remote.dto.LoginGoogleResponse
import com.todayeouido.tayapp.data.remote.dto.login.*
import com.todayeouido.tayapp.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Response

interface LoginRepository {
    suspend fun getUser(): UserPref
    suspend fun getRefreshToken(): String
    suspend fun setLoginUser(user: UserPref)
    suspend fun requestRegistration(registrationDto: RegistrationDto): Response<Void>
    suspend fun requestLogin(loginDto: LoginDto): Response<LoginResponse>
    suspend fun requestLogout(token: RefreshTokenDto): Response<LogoutResponse>
    suspend fun requestRefreshToken(token: RefreshTokenDto): Response<JwtRefreshResponse>
    suspend fun updateAccessToken(accessToken: String)
    suspend fun requestSnsLogin(sns: String, snsLoginDto: SnsLoginDto): Response<LoginResponse>
    suspend fun prefLogout()
    suspend fun googleAuth(request: LoginGoogleRequest): Response<LoginGoogleResponse>?
}
