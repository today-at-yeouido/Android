package com.todayeouido.tayapp.domain.use_case.favoritCategory

import android.util.Log
import com.todayeouido.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.todayeouido.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import com.todayeouido.tayapp.domain.repository.GetBillRepository
import com.todayeouido.tayapp.domain.repository.UserSettingRepository
import com.todayeouido.tayapp.domain.use_case.login.CheckLoginUseCase
import com.todayeouido.tayapp.utils.Resource
import com.todayeouido.tayapp.utils.UnAuthorizationError
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

