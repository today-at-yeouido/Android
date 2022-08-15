package com.example.tayapp.domain.use_case.login

import android.util.Log
import com.example.tayapp.data.remote.dto.login.RefreshTokenDto
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(): Boolean {
        val refreshToken = repository.getRefreshToken()
        return if (refreshToken.isNotBlank()) {
            val r = repository.requestRefreshToken(RefreshTokenDto(refreshToken))
            Log.d("##88", "refresh code ${r.code()}")
            when (r.code()) {
                200 -> {
                    r.body()?.let { repository.updateAccessToken(it.access) }
                }
                400 -> Log.d("##99", "code 400 ${r.body()}")
            }
            true
        } else {
            false
        }
    }
}
