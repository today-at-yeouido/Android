package com.example.tayapp.domain.use_case.login

import android.util.Log
import com.example.tayapp.data.remote.dto.login.SnsLoginDto
import com.example.tayapp.domain.repository.LoginRepository
import javax.inject.Inject

class RequestSnsUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(access : String){
        val r = repository.requestSnsLogin(SnsLoginDto(access))
        Log.d("##99", "r code ${r.code()}")
        Log.d("##99", "r body ${r.body()}")
    }
}