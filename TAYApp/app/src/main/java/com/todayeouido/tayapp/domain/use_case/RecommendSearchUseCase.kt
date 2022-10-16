package com.todayeouido.tayapp.domain.use_case

import android.util.Log
import com.todayeouido.tayapp.domain.repository.GetBillRepository
import com.todayeouido.tayapp.utils.NoConnectivityException
import com.todayeouido.tayapp.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecommendSearchUseCase @Inject constructor(private val repository: GetBillRepository) {
    operator fun invoke() = flow {
        emit(Resource.Loading())
        try {
            val res = repository.getRecommendSearch()
            when (res.code()) {
                200 -> {
                    emit(Resource.Success(res.body()!!.recommendSearch))
                }
                else -> emit(Resource.Error("Unexpected Error"))
            }
        } catch (e: NoConnectivityException) {
            emit(Resource.NetworkConnectionError())
        }
    }
}