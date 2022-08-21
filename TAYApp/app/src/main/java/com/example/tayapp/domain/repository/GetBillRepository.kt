package com.example.tayapp.domain.repository

import com.example.tayapp.data.remote.dto.bill.*
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.data.repository.GetBillRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Response

interface GetBillRepository {

    suspend fun getBillHome(): HomeBillDto
    suspend fun getBillDetail(billId: Int): Response<DetailBillDto>
    suspend fun getBillTable(billId: String): DetailBillTableDto

    suspend fun getBillRecent(page: Int): Response<List<BillDto>>
    suspend fun getBillMostViewed(): Response<List<MostViewedBillDto>>
    suspend fun getBillUserRecommended(): List<BillDto>
    suspend fun getBillUserRecentViewed(): List<BillDto>
    suspend fun getBillSearch(query: String): Response<List<ScrapBillDto>>

    suspend fun postAddScrap(bill: Int): Response<AddScrapResponseDto>
    suspend fun postDeleteScrap(bill: Int): Response<DeleteScrapResponseDto>
    suspend fun getBillScrap(): Response<List<ScrapBillDto>>

}

@InstallIn(ViewModelComponent::class)
@Module
abstract class GetBillRepositoryModule {
    @Binds
    abstract fun provideGetBillRepository(
        getBillRepoImpl: GetBillRepositoryImpl
    ): GetBillRepository
}