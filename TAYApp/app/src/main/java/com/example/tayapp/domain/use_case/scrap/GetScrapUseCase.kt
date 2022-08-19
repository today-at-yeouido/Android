package com.example.tayapp.domain.use_case.scrap

import android.util.Log
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.NoConnectivityException
import com.example.tayapp.utils.Resource
import com.example.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class GetScrapUseCase @Inject constructor(
    val repository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(): Flow<Resource<List<Bill>>> = flow {
        emit(Resource.Loading())
        try{
            val response = repository.getBillScrap()
            when (response.code()) {
                200 -> {
                    val bill = response.body()!!.bill.map { it.toDomain() }
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