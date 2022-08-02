package com.example.tayapp.domain.repository

import com.example.tayapp.data.remote.dto.BillDto
import com.example.tayapp.data.remote.dto.DetailBillDto
import com.example.tayapp.data.remote.dto.DetailBillTableDto
import com.example.tayapp.data.remote.dto.HomeBillDto
import com.example.tayapp.data.repository.GetBillRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

interface GetBillRepository {

    suspend fun getBillHome(): HomeBillDto
    suspend fun getBillDetail(billId: String): DetailBillDto
    suspend fun getBillTable(billId: String): DetailBillTableDto

    suspend fun getBillRecent(): List<BillDto>
    suspend fun getBillMostViewed(): List<BillDto>
    suspend fun getBillUserRecommended(): List<BillDto>
    suspend fun getBillUserRecentViewed(): List<BillDto>

}

@InstallIn(SingletonComponent::class)
@Module
abstract class GetBillRepositoryModule {
    @Binds
    abstract fun provideGetBillRepository(
        getBillRepoImpl : GetBillRepositoryImpl
    ): GetBillRepository
}