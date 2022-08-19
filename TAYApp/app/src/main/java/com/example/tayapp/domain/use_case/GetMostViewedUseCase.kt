package com.example.tayapp.domain.use_case

import android.util.Log
import com.example.tayapp.domain.model.MostViewedBill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.NoConnectivityException
import com.example.tayapp.utils.Resource
import com.example.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import retrofit2.Response
import javax.inject.Inject

class GetMostViewedUseCase @Inject
constructor(
    private val repository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(): Flow<Resource<List<MostViewedBill>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getBillMostViewed()
            when (response.code()) {
                200 -> {
                    val bill = response.body()!!.map { it.toDomain() }
                    emit(Resource.Success(bill))
                }
                401 -> {
                    checkLoginUseCase()
                    throw UnAuthorizationError()
                }
                400 -> emit(
                    Resource.Error(
                        response.errorBody().toString() ?: "An unexpected error occurred"
                    )
                )
                else -> emit(Resource.Error("Couldn't reach server"))
            }
        } catch (e: NoConnectivityException){
            emit(Resource.NetworkConnectionError("Network Error"))
        }
    }.retryWhen { cause, attempt ->
        Log.d("##88", "401 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}

