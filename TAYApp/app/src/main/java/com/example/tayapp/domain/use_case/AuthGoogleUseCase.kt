package com.example.tayapp.domain.use_case

import android.util.Log
import com.example.tayapp.data.remote.dto.LoginGoogleRequest
import com.example.tayapp.domain.repository.LoginRepository
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class AuthGoogleUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(authCode: String, clientId: String, clientSecret: String): String {
        val r = loginRepository.googleAuth(
            LoginGoogleRequest(
                grant_type = "authorization_code",
                client_id = clientId,
                client_secret = clientSecret,
                redirect_uri = "",
                code = authCode
            )
        )

        return r!!.body()!!.access_token
    }
}