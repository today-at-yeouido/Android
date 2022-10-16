package com.todayeouido.tayapp.data.repository

import com.todayeouido.tayapp.data.pref.PrefDataSource
import com.todayeouido.tayapp.data.pref.model.UserPref
import com.todayeouido.tayapp.data.remote.GoogleApi
import com.todayeouido.tayapp.data.remote.LoginApi
import com.todayeouido.tayapp.data.remote.dto.LoginGoogleRequest
import com.todayeouido.tayapp.data.remote.dto.LoginGoogleResponse
import com.todayeouido.tayapp.data.remote.dto.login.*
import com.todayeouido.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val pref: PrefDataSource,
    private val loginApi: LoginApi,
    private val google: GoogleApi
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

    override suspend fun updateAccessToken(accessToken: String){
        pref.updateAccessToken(accessToken)
    }

    override suspend fun requestRegistration(registrationDto: RegistrationDto): Response<Void> {
       return loginApi.postRegistration(registrationDto)
    }

    override suspend fun requestLogin(loginDto: LoginDto): Response<LoginResponse> {
        return loginApi.postLogin(loginDto)
    }

    override suspend fun requestLogout(token: RefreshTokenDto): Response<LogoutResponse> {
        return loginApi.postLogout(token)
    }

    override suspend fun requestRefreshToken(token: RefreshTokenDto): Response<JwtRefreshResponse> {
        return loginApi.postJwtRefresh(token)
    }

    override suspend fun requestSnsLogin(sns:String, snsLoginDto: SnsLoginDto): Response<LoginResponse> {
        return loginApi.postSocialLogin(sns, snsLoginDto)
    }

    override suspend fun prefLogout() {
        pref.logoutUser()
    }

    override suspend fun googleAuth(request: LoginGoogleRequest): Response<LoginGoogleResponse>? {
       return google.fetchGoogleAuthInfo(request)
    }
}