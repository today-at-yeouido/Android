package com.example.tayapp.domain.use_case

import com.example.tayapp.data.pref.model.toUser
import com.example.tayapp.domain.model.User
import com.example.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): User {
       return repository.getUser().toUser()
    }
}