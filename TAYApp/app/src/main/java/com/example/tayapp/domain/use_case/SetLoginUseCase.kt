package com.example.tayapp.domain.use_case

import android.util.Log
import com.example.tayapp.data.pref.model.UserData
import com.example.tayapp.data.remote.dto.LoginDto
import com.example.tayapp.domain.model.User
import com.example.tayapp.domain.repository.LoginRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SetLoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(user: User, password: String) {
        try {
            val o = repository.requestLogin(
                LoginDto(
                    email = user.email,
                    password = password,
                )
            )
            repository.setLoginUser(
                UserData(
                    accessToken = o.accessToken,
                    refreshToken = o.refreshToken,
                    id = o.user.pk,
                    email = o.user.email,
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