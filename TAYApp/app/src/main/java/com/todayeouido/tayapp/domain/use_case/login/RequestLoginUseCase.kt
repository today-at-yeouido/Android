package com.todayeouido.tayapp.domain.use_case.login

import android.util.Log
import com.todayeouido.tayapp.data.pref.model.UserPref
import com.todayeouido.tayapp.data.remote.dto.login.LoginDto
import com.todayeouido.tayapp.data.remote.dto.login.LoginResponse
import com.todayeouido.tayapp.data.remote.dto.login.toState
import com.todayeouido.tayapp.domain.repository.LoginRepository
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.utils.NoConnectivityException
import javax.inject.Inject

class RequestLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(email: String, pass: String): Boolean {
        try {
            val r = repository.requestLogin(LoginDto(email = email, password = pass))
            return when (r.code()) {
                200 -> {
                    setUser(r.body()!!)
                    UserState.user = r.body()!!.toState()
                    Log.d("##99", "로그인 유즈케이스${UserState.user}")
                    true
                }
                400 -> false
                else -> false
            }
        } catch (e: NoConnectivityException) {
            Log.d("##99", "네트워크 에러")
            return true
        }
    }

    private suspend fun setUser(user: LoginResponse) {
        repository.setLoginUser(
            UserPref(
                accessToken = user.accessToken,
                refreshToken = user.refreshToken,
                id = user.user.pk,
                email = user.user.email,
            )
        )
    }
}

