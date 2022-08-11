package com.example.tayapp.domain.use_case

import com.example.tayapp.domain.model.MostViewedBill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMostViewedUseCase @Inject
constructor(private val repository: GetBillRepository) {
    operator fun invoke(): Flow<Resource<List<MostViewedBill>>> = flow {
        val response = repository.getBillMostViewed()
        when (response.code()) {
            200 -> {
                emit(Resource.Loading())
                val bill = response.body()!!.map { it.toDomain() }
                emit(Resource.Success(bill))
            }
            400 -> emit(
                Resource.Error(
                    response.errorBody().toString() ?: "An unexpected error occurred"
                )
            )
            else -> emit(Resource.Error("Couldn't reach server"))
        }
    }

}

