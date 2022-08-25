package com.example.tayapp.data.repository

import com.example.tayapp.data.remote.BillApi
import com.example.tayapp.data.remote.UserSettingApi
import com.example.tayapp.data.remote.dto.scrap.DeleteScrapRequestDto
import com.example.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import com.example.tayapp.domain.repository.UserSettingRepository
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