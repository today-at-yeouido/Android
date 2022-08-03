package com.example.tayapp.domain.repository

import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.data.remote.dto.bill.LoginDto
import com.example.tayapp.data.remote.dto.bill.LoginResponse
import com.example.tayapp.data.remote.dto.login.JwtRefreshResponse
import com.example.tayapp.data.remote.dto.login.LogoutResponse
import com.example.tayapp.data.remote.dto.login.RefreshTokenDto
import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response

interface LoginRepository {

    suspend fun getUser(): UserPref
    suspend fun getRefreshToken() : String
    suspend fun setLoginUser(user: UserPref)
    suspend fun requestRegistration(registrationDto: RegistrationDto):Response<Void>
    suspend fun requestLogin(loginDto: LoginDto): Response<LoginResponse>
    suspend fun requestLogout(token: String): LogoutResponse
    suspend fun requestRefreshToken(token: RefreshTokenDto): Response<JwtRefreshResponse>
}

/** 인터페이스 주입을 위한 모듈 */
@InstallIn(SingletonComponent::class)
@Module
abstract class LoginRepoModule {

    @Binds
    abstract fun bindLoginRepo(
        loginImpl: LoginRepositoryImpl
    ): LoginRepository
}