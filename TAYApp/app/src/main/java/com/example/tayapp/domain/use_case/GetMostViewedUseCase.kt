package com.example.tayapp.domain.use_case

import com.example.tayapp.data.remote.dto.BillDto
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMostViewedUseCase @Inject
constructor(private val repository: GetBillRepository) {

    operator fun invoke(): Flow<Resource<List<BillDto>>> = flow {
        try {
            val billDto = repository.getBillMostViewed()
            emit(Resource.Success(billDto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
        }
    }
}

