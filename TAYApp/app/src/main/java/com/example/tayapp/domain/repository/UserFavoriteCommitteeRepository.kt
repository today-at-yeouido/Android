package com.example.tayapp.domain.repository

import com.example.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import com.example.tayapp.data.repository.GetBillRepositoryImpl
import com.example.tayapp.data.repository.UserCommitteeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Response

interface UserCommitteeRepository {

    suspend fun postUserCommittee(userFavoriteCommitteeDto: UserFavoriteCommitteeDto): Response<UserFavoriteCommitteeDto>
    suspend fun getUserCommittee(): Response<UserFavoriteCommitteeDto>
}

@InstallIn(ViewModelComponent::class)
@Module
abstract class UserCommitteeRepositoryModule {
    @Binds
    abstract fun provideUserCommitteeRepository(
        userCommitteeRepository: UserCommitteeRepositoryImpl
    ): UserCommitteeRepository
}