package com.todayeouido.tayapp.domain.use_case.login

import com.todayeouido.tayapp.data.remote.dto.login.RegistrationDto
import com.todayeouido.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class DeleteUserInfoUseCase @Inject constructor(private val repository: LoginRepository){

    suspend operator fun invoke(): Boolean {
        val response = repository.deleteUserInfo()
        return when (response.code()) {
            200 -> true
            else -> false
        }
    }

}

