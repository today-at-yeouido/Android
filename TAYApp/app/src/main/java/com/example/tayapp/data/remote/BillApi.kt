package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.Constants.GET_BILL_DETAIL
import com.example.tayapp.data.remote.Constants.GET_BILL_HOME
import com.example.tayapp.data.remote.Constants.GET_BILL_MOST_VIEWED
import com.example.tayapp.data.remote.Constants.GET_BILL_RECENT
import com.example.tayapp.data.remote.Constants.GET_BILL_SCRAP
import com.example.tayapp.data.remote.Constants.GET_BILL_SEARCH
import com.example.tayapp.data.remote.Constants.GET_BILL_USER_RECENT_VIEWED
import com.example.tayapp.data.remote.Constants.GET_BILL_USER_RECOMMENDED
import com.example.tayapp.data.remote.Constants.POST_ADD_SCRAP
import com.example.tayapp.data.remote.Constants.POST_DELETE_SCRAP
import com.example.tayapp.data.remote.dto.bill.*
import com.example.tayapp.data.remote.dto.scrap.*
import retrofit2.Response
import retrofit2.http.*

interface BillApi {
    @GET(GET_BILL_HOME)
    suspend fun getBillHome(
        @Query("page") page: Int = 0
    ) : HomeBillDto

    @GET("$GET_BILL_DETAIL{id}/")
    suspend fun getBillDetail(
        @Path("id") billId: Int
    ) : Response<DetailBillDto>

    @GET("$GET_BILL_DETAIL/{id}/table")
    suspend fun getBillTable(
        @Path("id") id: String
    ) : DetailBillTableDto

    @GET(GET_BILL_RECENT)
    suspend fun getBillRecent(
        @Query("page") page: Int = 0
    ) : Response<List<BillDto>>

    @GET(GET_BILL_SEARCH)
    suspend fun getBillSearch(
        @Query("query") query: String
    ) : Response<List<ScrapBillDto>>

    @GET(GET_BILL_MOST_VIEWED)
    suspend fun getBillMostViewed() : Response<List<MostViewedBillDto>>

    @GET(GET_BILL_USER_RECOMMENDED)
    suspend fun getBillUserRecommended() : List<BillDto>

    @GET(GET_BILL_USER_RECENT_VIEWED)
    suspend fun getBillUserRecentViewed() : List<BillDto>

    @POST(POST_ADD_SCRAP)
    suspend fun postAddScrap(
        @Body bill: AddScrapRequestDto
    ) : Response<AddScrapResponseDto>

    @POST(POST_DELETE_SCRAP)
    suspend fun postDeleteScrap(
        @Body bill: DeleteScrapRequestDto
    ) : Response<DeleteScrapResponseDto>

    @GET(GET_BILL_SCRAP)
    suspend fun getBillScrap() : Response<List<ScrapBillDto>>
}