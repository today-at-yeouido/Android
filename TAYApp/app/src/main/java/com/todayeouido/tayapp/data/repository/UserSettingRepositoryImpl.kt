package com.todayeouido.tayapp.data.repository

import com.todayeouido.tayapp.data.remote.UserSettingApi
import com.todayeouido.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import com.todayeouido.tayapp.domain.repository.UserSettingRepository
import retrofit2.Response
import javax.inject.Inject

class UserSettingRepositoryImpl  @Inject
constructor(private val UserSettingApi: UserSettingApi) : UserSettingRepository {
    override suspend fun postUserCommittee(userFavoriteCommitteeDto: UserFavoriteCommitteeDto): Response<UserFavoriteCommitteeDto> {
        return UserSettingApi.postUserCommittee(userFavoriteCommitteeDto)
    }

    override suspend fun getUserCommittee(): Response<UserFavoriteCommitteeDto> {
        return UserSettingApi.getUserCommittee()
    }
}