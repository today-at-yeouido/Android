package com.example.tayapp.domain.repository

import com.example.tayapp.data.remote.dto.RecommendSearchDto
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
    suspend fun getBillUserRecentViewed(): Response<List<BillDto>>
    suspend fun getBillSearch(query: String, page: Int): Response<List<ScrapBillDto>>

    suspend fun postAddScrap(bill: Int): Response<AddScrapResponseDto>
    suspend fun postDeleteScrap(bill: Int): Response<DeleteScrapResponseDto>
    suspend fun getBillScrap(): Response<List<ScrapBillDto>>
    suspend fun getAutoComplete(query: String): Response<AutoCompleteDto>
    suspend fun getRecommendSearch(): Response<RecommendSearchDto>

}


