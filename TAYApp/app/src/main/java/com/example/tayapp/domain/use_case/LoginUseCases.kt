package com.example.tayapp.domain.use_case

import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.domain.use_case.login.GetUserUseCase
import com.example.tayapp.domain.use_case.login.RequestLoginUseCase
import com.example.tayapp.domain.use_case.login.RequestRegisterUseCase
import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val requestLoginUseCase: RequestLoginUseCase,
    val requestRegisterUseCase: RequestRegisterUseCase,
    val checkLoginUseCase: CheckLoginUseCase,
    val getUserUseCase: GetUserUseCase
)