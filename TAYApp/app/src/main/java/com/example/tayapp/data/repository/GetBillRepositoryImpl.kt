package com.example.tayapp.data.repository

import com.example.tayapp.data.remote.BillApi
import com.example.tayapp.data.remote.dto.RecommendSearchDto
import com.example.tayapp.data.remote.dto.bill.*
import com.example.tayapp.data.remote.dto.scrap.*
import com.example.tayapp.domain.repository.GetBillRepository
import retrofit2.Response
import javax.inject.Inject

class GetBillRepositoryImpl @Inject
    constructor(private val billApi: BillApi) : GetBillRepository {

    override suspend fun getBillHome(): HomeBillDto {
        return billApi.getBillHome()
    }

    override suspend fun getBillDetail(billId: Int): Response<DetailBillDto> {
        return billApi.getBillDetail(billId)
    }

    override suspend fun getBillTable(billId: Int): Response<ComparisonTableDto> {
        return billApi.getBillTable(billId)
    }

    override suspend fun getBillRecent(page: Int): Response<List<BillDto>> {
        return billApi.getBillRecent(page)
    }

    override suspend fun getBillMostViewed(): Response<List<MostViewedBillDto>> {
        return billApi.getBillMostViewed()
    }

    override suspend fun getBillUserRecommended(): Response<List<RecommendBillDto>> {
        return billApi.getBillUserRecommended()
    }

    override suspend fun getBillUserRecentViewed(): Response<List<BillDto>> {
        return billApi.getBillUserRecentViewed()
    }

    override suspend fun getBillHomeCommittee(page: Int, committee: String): Response<HomeCommitteeBillDto> {
        return billApi.getBillHomeCommittee(page, committee)
    }

    override suspend fun getBillSearch(query: String, page: Int): Response<List<ScrapBillDto>> {
        return billApi.getBillSearch(query, page)
    }

    override suspend fun postAddScrap(bill: Int): Response<AddScrapResponseDto> {
        return billApi.postAddScrap(AddScrapRequestDto(bill))
    }

    override suspend fun postDeleteScrap(bill: Int): Response<DeleteScrapResponseDto> {
        return billApi.postDeleteScrap(DeleteScrapRequestDto(bill))
    }

    override suspend fun getBillScrap(): Response<List<ScrapBillDto>> {
        return billApi.getBillScrap()
    }

    override suspend fun getAutoComplete(query: String): Response<AutoCompleteDto> {
        return billApi.getAutoComplete(query)
    }

    override suspend fun getRecommendSearch(): Response<RecommendSearchDto> {
        return billApi.getRecommendSearch()
    }

    override suspend fun getBillGroup(groupid: String): Response<ScrapBillDto> {
        return billApi.getBillGroup(groupid)
    }
}