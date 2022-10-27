package com.todayeouido.tayapp.domain.use_case

import android.util.Log
import com.todayeouido.tayapp.domain.model.MostViewedBill
import com.todayeouido.tayapp.domain.model.toDomain
import com.todayeouido.tayapp.domain.repository.GetBillRepository
import com.todayeouido.tayapp.domain.use_case.login.CheckLoginUseCase
import com.todayeouido.tayapp.utils.NoConnectivityException
import com.todayeouido.tayapp.utils.Resource
import com.todayeouido.tayapp.utils.UnAuthorizationError
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
            emit(Resource.NetworkConnectionError())
        }
    }.retryWhen { cause, attempt ->
        Log.d("##88", "401 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}
