package com.example.tayapp.domain.use_case

import com.example.tayapp.data.remote.dto.bill.MostViewedBillDto
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMostViewedUseCase @Inject
constructor(private val repository: GetBillRepository) {

    operator fun invoke(): Flow<Resource<List<MostViewedBillDto>>> = flow {
        val response = repository.getBillMostViewed()
        when (response.code()) {
            200 -> {
                val billDto = response.body()!!
                emit(Resource.Success(billDto))
            }
            400 -> emit(
                Resource.Error(
                    response.errorBody().toString() ?: "An unexpected error occurred"
                )
            )
            else -> emit(Resource.Error("Couldn't reach server"))
        }
    }.flowOn(Dispatchers.Default)

}

