package com.example.tayapp.data.remote

import com.example.tayapp.data.remote.Constants.GET_AUTOCOMPLETE
import com.example.tayapp.data.remote.Constants.GET_BILL_DETAIL
import com.example.tayapp.data.remote.Constants.GET_BILL_GROUP
import com.example.tayapp.data.remote.Constants.GET_BILL_HOME
import com.example.tayapp.data.remote.Constants.GET_BILL_HOME_COMMITTEE
import com.example.tayapp.data.remote.Constants.GET_BILL_MOST_VIEWED
import com.example.tayapp.data.remote.Constants.GET_BILL_RECENT
import com.example.tayapp.data.remote.Constants.GET_BILL_SCRAP
import com.example.tayapp.data.remote.Constants.GET_BILL_SEARCH
import com.example.tayapp.data.remote.Constants.GET_BILL_USER_RECENT_VIEWED
import com.example.tayapp.data.remote.Constants.GET_BILL_USER_RECOMMENDED
import com.example.tayapp.data.remote.Constants.GET_RECCOMEND_SEARCH
import com.example.tayapp.data.remote.Constants.POST_ADD_SCRAP
import com.example.tayapp.data.remote.Constants.POST_DELETE_SCRAP
import com.example.tayapp.data.remote.dto.RecommendSearchDto
import com.example.tayapp.data.remote.dto.bill.*
import com.example.tayapp.data.remote.dto.scrap.*
import retrofit2.Response
import retrofit2.http.*

interface BillApi {
    @GET(GET_BILL_HOME)
    suspend fun getBillHome(
        @Query("page") page: Int = 0
    ): HomeBillDto

    @GET("$GET_BILL_DETAIL{id}/")
    suspend fun getBillDetail(
        @Path("id") billId: Int
    ): Response<DetailBillDto>

    @GET("$GET_BILL_DETAIL/{id}/table")
    suspend fun getBillTable(
        @Path("id") id: String
    ): DetailBillTableDto

    @GET(GET_BILL_RECENT)
    suspend fun getBillRecent(
        @Query("page") page: Int
    ): Response<List<BillDto>>

    @GET(GET_BILL_SEARCH)
    suspend fun getBillSearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<List<ScrapBillDto>>

    @GET(GET_BILL_MOST_VIEWED)
    suspend fun getBillMostViewed(): Response<List<MostViewedBillDto>>

    @GET(GET_BILL_USER_RECOMMENDED)
    suspend fun getBillUserRecommended(): Response<List<RecommendBillDto>>

    @GET(GET_BILL_USER_RECENT_VIEWED)
    suspend fun getBillUserRecentViewed(): Response<List<BillDto>>

    @GET(GET_BILL_HOME_COMMITTEE)
    suspend fun getBillHomeCommittee(
        @Query("page") page: Int,
        @Query("committee") committee : String
    ): Response<HomeCommitteeBillDto>

    @POST(POST_ADD_SCRAP)
    suspend fun postAddScrap(
        @Body bill: AddScrapRequestDto
    ): Response<AddScrapResponseDto>

    @POST(POST_DELETE_SCRAP)
    suspend fun postDeleteScrap(
        @Body bill: DeleteScrapRequestDto
    ): Response<DeleteScrapResponseDto>

    @GET(GET_BILL_SCRAP)
    suspend fun getBillScrap(): Response<List<ScrapBillDto>>

    @GET(GET_AUTOCOMPLETE)
    suspend fun getAutoComplete(
        @Query("query") query: String
    ): Response<AutoCompleteDto>

    @GET(GET_RECCOMEND_SEARCH)
    suspend fun getRecommendSearch(): Response<RecommendSearchDto>

    @GET(GET_BILL_GROUP)
    suspend fun getBillGroup(
        @Query("groupid") groupid: String,
    ): Response<ScrapBillDto>
}