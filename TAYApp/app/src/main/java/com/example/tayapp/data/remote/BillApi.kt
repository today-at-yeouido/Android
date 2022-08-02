package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.Constants.GET_BILL_DETAIL
import com.example.tayapp.data.remote.Constants.GET_BILL_HOME
import com.example.tayapp.data.remote.Constants.GET_BILL_MOST_VIEWED
import com.example.tayapp.data.remote.Constants.GET_BILL_RECENT
import com.example.tayapp.data.remote.Constants.GET_BILL_USER_RECENT_VIEWED
import com.example.tayapp.data.remote.Constants.GET_BILL_USER_RECOMMENDED
import com.example.tayapp.data.remote.dto.BillDto
import com.example.tayapp.data.remote.dto.DetailBillTableDto
import com.example.tayapp.data.remote.dto.DetailBillDto
import com.example.tayapp.data.remote.dto.HomeBillDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BillApi {
    @GET(GET_BILL_HOME)
    suspend fun getBillHome(
        @Query("page") page: Int = 0
    ) : HomeBillDto

    @GET("$GET_BILL_DETAIL/{id}")
    suspend fun getBillDetail(
        @Path("id") billId: String
    ) : DetailBillDto

    @GET("$GET_BILL_DETAIL/{id}/table")
    suspend fun getBillTable(
        @Path("id") id: String
    ) : DetailBillTableDto

    @GET(GET_BILL_RECENT)
    suspend fun getBillRecent() : List<BillDto>

    @GET(GET_BILL_MOST_VIEWED)
    suspend fun getBillMostViewed() : List<BillDto>

    @GET(GET_BILL_USER_RECOMMENDED)
    suspend fun getBillUserRecommended() : List<BillDto>

    @GET(GET_BILL_USER_RECENT_VIEWED)
    suspend fun getBillUserRecentViewed() : List<BillDto>

}