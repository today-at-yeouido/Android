package com.example.tayapp.domain.use_case

import android.util.Log
import com.example.tayapp.data.pref.model.UserData
import com.example.tayapp.data.remote.dto.LoginDto
import com.example.tayapp.domain.model.User
import com.example.tayapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SetLoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(user: User, password: String) {
        withContext(Dispatchers.Default) {
            try {
                val u = repository.requestLogin(
                    LoginDto(
                        email = user.email,
                        password = password,
                    )
                )
                repository.setLoginUser(
                    UserData(
                        accessToken = u.accessToken,
                        refreshToken = u.refreshToken,
                        id = u.user.pk,
                        email = u.user.email,
                        name = user.name
                    )
                )
            } catch (e: HttpException) {
                Log.d("##12", e.message())
            } catch (e: IOException) {
                Log.d("##12", "${e.message}")
            }
        }
    }
}