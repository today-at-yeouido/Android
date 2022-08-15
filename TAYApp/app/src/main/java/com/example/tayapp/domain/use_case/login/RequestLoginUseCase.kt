package com.example.tayapp.domain.use_case.login

import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.data.remote.dto.login.LoginDto
import com.example.tayapp.data.remote.dto.login.LoginResponse
import com.example.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class RequestLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(user: LoginDto): Boolean {
            val r = repository.requestLogin(user)
            return when (r.code()) {
                200 -> {
                    setUser(r.body()!!)
                     true
                }
                400 -> false
                else -> false
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

