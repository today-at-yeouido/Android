package com.example.tayapp.data.repository

import com.example.tayapp.data.remote.BillApi
import com.example.tayapp.data.remote.dto.bill.*
import com.example.tayapp.data.remote.dto.scrap.AddScrapRequestDto
import com.example.tayapp.data.remote.dto.scrap.AddScrapResponseDto
import com.example.tayapp.data.remote.dto.scrap.DeleteScrapRequestDto
import com.example.tayapp.data.remote.dto.scrap.DeleteScrapResponseDto
import com.example.tayapp.domain.repository.GetBillRepository
import retrofit2.Response
import javax.inject.Inject

class GetBillRepositoryImpl @Inject
    constructor(private val billApi: BillApi) : GetBillRepository {

    override suspend fun getBillHome(): HomeBillDto {
        return billApi.getBillHome()
    }

    override suspend fun getBillDetail(billId : String): DetailBillDto {
        return billApi.getBillDetail(billId)
    }

    override suspend fun getBillTable(billId: String): DetailBillTableDto {
        return billApi.getBillTable(billId)
    }

    override suspend fun getBillRecent(page: Int): Response<List<BillDto>> {
        return billApi.getBillRecent(page)
    }

    override suspend fun getBillMostViewed(): Response<List<MostViewedBillDto>> {
        return billApi.getBillMostViewed()
    }

    override suspend fun getBillUserRecommended(): List<BillDto> {
        return billApi.getBillUserRecommended()
    }

    override suspend fun getBillUserRecentViewed(): List<BillDto> {
        return billApi.getBillUserRecentViewed()
    }

    override suspend fun getBillSearch(query: String): Response<List<BillDto>> {
        return billApi.getBillSearch(query)
    }

    override suspend fun postAddScrap(bill: Int): Response<AddScrapResponseDto>{
        return billApi.postAddScrap(AddScrapRequestDto(bill))
    }

    override suspend fun postDeleteScrap(bill: Int): Response<DeleteScrapResponseDto> {
        return billApi.postDeleteScrap(DeleteScrapRequestDto(bill))
    }

    override suspend fun getBillScrap(): Response<List<BillDto>> {
        return billApi.getBillScrap()
    }
}