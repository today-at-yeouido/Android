package com.example.tayapp.domain.use_case.login

import android.util.Log
import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.data.remote.dto.LoginDto
import com.example.tayapp.data.remote.dto.LoginResponse
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(user: LoginDto) {
        withContext(Dispatchers.Default) {
            val r = repository.requestLogin(user)
            when (r.code()) {
                200 -> { setUser(r.body()!!) }
                400 -> Log.d("##77", "아이디, 비밀번호 체크")
                else -> Log.d("##77", "알 수 없는 에러")
            }
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