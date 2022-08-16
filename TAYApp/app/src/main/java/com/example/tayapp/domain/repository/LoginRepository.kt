package com.example.tayapp.domain.repository


import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.data.remote.dto.login.*
import com.example.tayapp.data.repository.LoginRepositoryImpl
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
    suspend fun requestSnsLogin(snsLoginDto: SnsLoginDto): Response<LoginResponse>
    suspend fun prefLogout()
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