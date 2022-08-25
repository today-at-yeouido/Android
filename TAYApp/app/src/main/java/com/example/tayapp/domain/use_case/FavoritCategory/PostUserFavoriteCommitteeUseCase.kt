package com.example.tayapp.domain.use_case.FavoritCategory

import android.util.Log
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import com.example.tayapp.domain.repository.GetBillRepository
import com.example.tayapp.domain.repository.UserSettingRepository
import com.example.tayapp.domain.use_case.login.CheckLoginUseCase
import com.example.tayapp.utils.Resource
import com.example.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class PostUserFavoriteCommitteeUseCase @Inject constructor(
    val repository: UserSettingRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(on: List<String>): Flow<Resource<List<String>>> = flow {
        val response = repository.postUserCommittee(UserFavoriteCommitteeDto(on))
        emit(Resource.Loading())
        when (response.code()) {
            200 -> {
                emit(Resource.Success(response.body()!!.on))
            }
            400 -> {
//                checkLoginUseCase()
                emit(Resource.Error("there is no id for bill"))
            }
            401 -> {
                checkLoginUseCase()
                throw UnAuthorizationError()
            }
            406 -> {
                emit(Resource.Error("there is already scrap data in DB"))
            }
            else -> emit(Resource.Error("Couldn't reach server"))
        }
    }.retryWhen { cause, attempt ->
        Log.e("##88", "Retry 401 Error, unAuthorization token")
        cause is UnAuthorizationError || attempt < 3
    }
}

