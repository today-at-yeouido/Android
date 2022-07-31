package com.example.tayapp.data.repository

import com.example.tayapp.data.pref.LoginPref
import com.example.tayapp.domain.repository.LoginRepository
import com.example.tayapp.domain.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val pref: LoginPref
) : LoginRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun getLoginUser(): User {
        return withContext(scope.coroutineContext) {
            pref.getLoginUser().first()
        }
    }

    override suspend fun setLoginUser(user: User) {
        scope.launch { pref.setLoginUser(user) }
    }
}