package com.example.tayapp.domain.use_case.login

import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestRegisterUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(requestRegistrationDto: RegistrationDto):Boolean {
            val response = repository.requestRegistration(requestRegistrationDto)
            return when (response.code()) {
                201 -> true
                else -> false
            }
        }
}