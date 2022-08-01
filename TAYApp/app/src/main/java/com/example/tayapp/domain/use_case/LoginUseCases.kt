package com.example.tayapp.domain.use_case

import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val setLoginUseCase: SetLoginUseCase,
    val getUserUseCase: GetUserUseCase
)