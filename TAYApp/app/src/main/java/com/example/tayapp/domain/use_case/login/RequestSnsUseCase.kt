package com.example.tayapp.domain.use_case.login

import android.util.Log
import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.data.pref.model.toState
import com.example.tayapp.data.remote.dto.login.LoginResponse
import com.example.tayapp.data.remote.dto.login.SnsLoginDto
import com.example.tayapp.data.remote.dto.login.toPref
import com.example.tayapp.domain.repository.LoginRepository
import com.example.tayapp.presentation.states.LoginState
import javax.inject.Inject

class RequestSnsUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(access: String): Boolean {
        val r = repository.requestSnsLogin(SnsLoginDto(access))
        return when (r.code()) {
            200 -> {
                Log.d("##99","sns success ${r.code()}")
                val user = r.body()!!.toPref()
                repository.setLoginUser(user)
                LoginState.user = user.toState()
                true
            }
            else -> {
                Log.d("##99","sns fali ${r.code()}")
                false
            }
        }
    }
}