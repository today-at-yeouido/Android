package com.example.tayapp.domain.use_case.login

import com.example.tayapp.data.pref.model.UserPref
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): UserPref {
        return repository.getUser()
    }
}