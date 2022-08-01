package com.example.tayapp.data.repository

import com.example.tayapp.data.remote.BillApi
import com.example.tayapp.data.remote.dto.BillDto
import com.example.tayapp.data.remote.dto.DetailBillDto
import com.example.tayapp.data.remote.dto.DetailBillTableDto
import com.example.tayapp.data.remote.dto.HomeBillDto
import com.example.tayapp.domain.repository.GetBillRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getBillRecent(): BillDto {
        return billApi.getBillRecent()
    }

    override suspend fun getBillMostViewed(): List<BillDto> {
        return billApi.getBillMostViewed()
    }

    override suspend fun getBillUserRecommended(): BillDto {
        return billApi.getBillUserRecommended()
    }

    override suspend fun getBillUserRecentViewed(): BillDto {
        return billApi.getBillUserRecentViewed()
    }
}