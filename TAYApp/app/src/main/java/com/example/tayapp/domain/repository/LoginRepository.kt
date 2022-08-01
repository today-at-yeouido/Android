package com.example.tayapp.domain.repository

import com.example.tayapp.data.pref.model.UserData
import com.example.tayapp.data.remote.dto.*
import com.example.tayapp.data.repository.LoginRepositoryImpl
import com.example.tayapp.domain.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

interface LoginRepository {

    suspend fun getUser(): UserData
    suspend fun setLoginUser(user: UserData)
    suspend fun requestRegistration(registrationDto: RegistrationDto)
    suspend fun requestLogin(loginDto: LoginDto): LoginResponse
    suspend fun requestLogout(token: String): LogoutResponse
    suspend fun requestRefreshToken(token: String): JwtRefreshResponse
}

/** 인터페이스 주입을 위한 모듈 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class LoginRepoModule {

    @Binds
    abstract fun bindLoginRepo(
        loginImpl: LoginRepositoryImpl
    ): LoginRepository
}