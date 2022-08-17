package com.example.tayapp.domain.use_case.scrap

import android.util.Log
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostAddScrapUseCase @Inject constructor(
    val repository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(bill: Int): Flow<Resource<AddScrapResponseDto>> = flow {
        val response = repository.postAddScrap(bill)
        emit(Resource.Loading())
        Log.d("스크랩", response.code().toString())
        when (response.code()) {
            202 -> {
                emit(Resource.Success(response.body()!!))
            }
            400 -> {
//                checkLoginUseCase()
                emit(Resource.Error("there is no id for bill"))
            }
            401 -> {
                checkLoginUseCase()
//                val response = repository.postAddScrap(bill)
                emit(Resource.Error("Authentication credentials were not provided"))
            }
            406 -> {
                emit(Resource.Error("there is already scrap data in DB"))
            }
            else -> emit(Resource.Error("Couldn't reach server"))
        }
    }
}