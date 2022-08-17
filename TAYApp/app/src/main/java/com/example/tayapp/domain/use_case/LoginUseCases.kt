package com.example.tayapp.domain.use_case

import com.example.tayapp.domain.use_case.login.*
import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val requestLoginUseCase: RequestLoginUseCase,
    val requestRegisterUseCase: RequestRegisterUseCase,
    val checkLoginUseCase: CheckLoginUseCase,
    val getUserUseCase: GetUserUseCase,
    val requestSnsUseCase: RequestSnsUseCase
)