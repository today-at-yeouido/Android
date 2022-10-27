package com.todayeouido.tayapp.domain.repository

import com.todayeouido.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.todayeouido.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import com.todayeouido.tayapp.data.repository.UserSettingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Response

interface UserSettingRepository {

    suspend fun postUserCommittee(userFavoriteCommitteeDto: UserFavoriteCommitteeDto): Response<UserFavoriteCommitteeDto>
    suspend fun getUserCommittee(): Response<UserFavoriteCommitteeDto>
}

@InstallIn(ViewModelComponent::class)
@Module
abstract class UserSettingRepositoryModule {
    @Binds
    abstract fun provideUserSettingRepository(
        userSettingRepository: UserSettingRepositoryImpl
    ): UserSettingRepository
}