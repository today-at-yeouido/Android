package com.example.tayapp.domain.use_case.scrap

import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.domain.model.toDomain
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetScrapUseCase @Inject constructor(
    val repository: GetBillRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(bill: Int): Flow<Resource<List<Bill>>> = flow {
        val response = repository.getBillScrap()
        emit(Resource.Loading())
        when (response.code()) {
            202 -> {
                val bill = response.body()!!.map { it.toDomain() }
                emit(Resource.Success(bill))
            }
            401 -> {
                checkLoginUseCase()
                val response = repository.getBillScrap()
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