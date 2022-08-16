package com.example.tayapp.domain.use_case.login

import android.util.Log
import com.example.tayapp.data.remote.dto.login.LogoutResponse
import com.example.tayapp.data.remote.dto.login.RefreshTokenDto
import com.example.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class RequestLogoutUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {

    suspend operator fun invoke(refToken: String): Boolean {
        val r = repository.requestLogout(RefreshTokenDto(refToken))
        return when (r.code()) {
            200 -> {
                repository.prefLogout()
                Log.d("##99", "로그아웃 성공 ${r.body()}")
                true
            }
            401 -> {
                checkLoginUseCase()
                val c = repository.requestLogout(RefreshTokenDto(refToken))
                if (c.code() == 200) {
                    Log.d("##99", "로그아웃 성공 ${r.body()}")
                    repository.prefLogout()
                }
                false
            }
            else -> {
                Log.d("##99", "로그아웃 ${r.body()}, 코드 ${r.code()}")
                false
            }
        }
    }

}