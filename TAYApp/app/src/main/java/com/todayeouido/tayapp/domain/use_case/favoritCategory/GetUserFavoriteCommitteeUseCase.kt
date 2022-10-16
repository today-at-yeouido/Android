package com.todayeouido.tayapp.domain.use_case.favoritCategory

import android.util.Log
import com.todayeouido.tayapp.domain.repository.UserSettingRepository
import com.todayeouido.tayapp.domain.use_case.login.CheckLoginUseCase
import com.todayeouido.tayapp.utils.NoConnectivityException
import com.todayeouido.tayapp.utils.Resource
import com.todayeouido.tayapp.utils.UnAuthorizationError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class GetUserFavoriteCommitteeUseCase @Inject constructor(
    private val repository: UserSettingRepository,
    private val checkLoginUseCase: CheckLoginUseCase
) {
    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())
        try{
            val response = repository.getUserCommittee()
            when (response.code()) {
                200 -> {
                    val on = response.body()!!.on
                    emit(Resource.Success(on))
                }
                400 -> emit(
                    Resource.Error(
                        response.errorBody().toString() ?: "An unexpected error occurred"
                    )
                )
                401 -> {
                    checkLoginUseCase()
                    throw UnAuthorizationError()
                }
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
