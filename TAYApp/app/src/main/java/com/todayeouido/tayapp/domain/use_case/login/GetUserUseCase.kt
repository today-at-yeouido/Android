package com.todayeouido.tayapp.domain.use_case.login

import com.todayeouido.tayapp.data.pref.model.UserPref
import com.todayeouido.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): UserPref {
        return repository.getUser()
    }
}