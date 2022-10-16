package com.todayeouido.tayapp.domain.repository

import com.todayeouido.tayapp.data.remote.dto.RecommendSearchDto
import com.todayeouido.tayapp.data.remote.dto.bill.*
import com.todayeouido.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.todayeouido.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.todayeouido.tayapp.data.repository.GetBillRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Response

interface GetBillRepository {

    suspend fun getBillHome(): HomeBillDto
    suspend fun getBillDetail(billId: Int): Response<DetailBillDto>
    suspend fun getBillTable(billId: Int): Response<ComparisonTableDto>

    suspend fun getBillRecent(page: Int): Response<List<BillDto>>
    suspend fun getBillMostViewed(): Response<List<MostViewedBillDto>>
    suspend fun getBillUserRecommended(): Response<List<RecommendBillDto>>
    suspend fun getBillUserRecentViewed(): Response<List<BillDto>>
    suspend fun getBillHomeCommittee(page: Int, committee: String) : Response<HomeCommitteeBillDto>
    suspend fun getBillSearch(query: String, page: Int): Response<List<ScrapBillDto>>
    suspend fun getBillGroup(groupid: String): Response<ScrapBillDto>

    suspend fun postAddScrap(bill: Int): Response<AddScrapResponseDto>
    suspend fun postDeleteScrap(bill: Int): Response<DeleteScrapResponseDto>
    suspend fun getBillScrap(): Response<List<ScrapBillDto>>
    suspend fun getAutoComplete(query: String): Response<AutoCompleteDto>
    suspend fun getRecommendSearch(): Response<RecommendSearchDto>

}


