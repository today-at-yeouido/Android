package com.example.tayapp.domain.use_case.login

import android.util.Log
import com.example.tayapp.data.remote.dto.RefreshTokenDto
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(): Boolean =
        withContext(Dispatchers.Default) {
            val refreshToken = repository.getRefreshToken()
            if (refreshToken.isNotBlank()) {
                val r = repository.requestRefreshToken(RefreshTokenDto(refreshToken))
                when (r.code()) {
                    200 -> Log.d("##99", "code 200 ${r.body()}")
                    400 -> Log.d("##99", "code 400 ${r.body()}")
                }
                return@withContext true
            } else {
                return@withContext false
            }
        }
}
