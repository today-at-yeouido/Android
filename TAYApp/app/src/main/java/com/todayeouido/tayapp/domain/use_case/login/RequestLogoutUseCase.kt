package com.todayeouido.tayapp.domain.use_case.login

import android.util.Log
import com.todayeouido.tayapp.data.remote.dto.login.LogoutResponse
import com.todayeouido.tayapp.data.remote.dto.login.RefreshTokenDto
import com.todayeouido.tayapp.domain.repository.LoginRepository
import com.todayeouido.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RequestLogoutUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    suspend operator fun invoke(refToken: String): Flow<Boolean> = flow {
        emit(false)
        val r = repository.requestLogout(RefreshTokenDto(refToken))
        when (r.code()) {
            200 -> {
                repository.prefLogout()
                Log.d("##99", "로그아웃 성공 ${r.body()}")
                emit(true)
            }
            401 -> {
                checkLoginUseCase()
                throw UnAuthorizationError()
            }
            else -> {
                Log.d("##99", "로그아웃 ${r.body()}, 코드 ${r.code()}")
                emit(false)
            }
        }
    }.retryWhen { cause, attempt ->
        Log.d("##88", "401 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}

