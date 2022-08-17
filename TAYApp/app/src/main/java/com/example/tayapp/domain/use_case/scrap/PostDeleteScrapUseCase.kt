package com.example.tayapp.domain.use_case.scrap

import android.util.Log
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.Resource
import com.example.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class PostDeleteScrapUseCase @Inject constructor(
    val repository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(bill: Int): Flow<Resource<DeleteScrapResponseDto>> = flow {
        val response = repository.postDeleteScrap(bill)
        emit(Resource.Loading())
        when (response.code()) {
            202 -> {
                emit(Resource.Success(response.body()!!))
            }
            400 -> {
                emit(Resource.Error("there is no id for bill"))
            }
            401 -> {
                checkLoginUseCase()
                throw UnAuthorizationError()
            }
            406 -> {
                emit(Resource.Error("there is no scrap data in DB"))
            }
            else -> emit(Resource.Error("Couldn't reach server"))
        }
    }.retryWhen { cause, attempt ->
        Log.d("##88", "404 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}