package com.example.tayapp.domain.use_case

import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.utils.NoConnectivityException
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecommendSearchUseCase @Inject constructor(private val repository: GetBillRepository) {
    operator fun invoke() = flow {
        try {
            val res = repository.getRecommendSearch()
            when (res.code()) {
                200 -> {
                    emit(Resource.Success(res.body()!!))
                }
                else -> emit(Resource.Error("Unexpected Error"))
            }
        } catch (e: NoConnectivityException) {
            emit(Resource.NetworkConnectionError())
        }
    }
}