package com.example.tayapp.domain.use_case.login

import com.example.tayapp.data.remote.dto.login.RegistrationDto
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestRegisterUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(requestRegistrationDto: RegistrationDto) =
        withContext(Dispatchers.Default) {
            val response = repository.requestRegistration(requestRegistrationDto)
            when (response.code()) {
                201 -> true
                else -> false
            }
        }
}