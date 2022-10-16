package com.todayeouido.tayapp.domain.use_case

import com.todayeouido.tayapp.data.remote.dto.LoginGoogleRequest
import com.todayeouido.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class AuthGoogleUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(authCode: String, clientId: String, clientSecret: String): String {
        val res = loginRepository.googleAuth(
            LoginGoogleRequest(
                grant_type = "authorization_code",
                client_id = clientId,
                client_secret = clientSecret,
                redirect_uri = "",
                code = authCode
            )
        )

        return res!!.body()!!.access_token
    }
}