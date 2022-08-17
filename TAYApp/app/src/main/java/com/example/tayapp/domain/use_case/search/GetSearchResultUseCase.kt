package com.example.tayapp.domain.use_case.search

import android.util.Log
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.domain.model.MostViewedBill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val repository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
){
    operator fun invoke(query: String): Flow<Resource<List<BillDto>>> = flow {

        emit(Resource.Loading())
        val response = repository.getBillSearch(query)
        Log.d("asdf","${response.code()}")
        when (response.code()) {
            200 -> { emit(Resource.Success(response.body()!!)) }
            400 -> emit(
                Resource.Error(
                    response.errorBody().toString() ?: "An unexpected error occurred"
                )
            )
            401 -> {
                checkLoginUseCase()
                val response = repository.getBillSearch(query)
                emit(Resource.Success(response.body()!!))
            }
            else -> emit(Resource.Error("Couldn't reach server"))
        }
    }
}