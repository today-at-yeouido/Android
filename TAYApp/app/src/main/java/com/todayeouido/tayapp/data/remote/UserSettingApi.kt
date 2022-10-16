package com.todayeouido.tayapp.data.remote

import com.todayeouido.tayapp.data.remote.Constants.GET_USER_COMMITTEE
import com.todayeouido.tayapp.data.remote.Constants.POST_USER_COMMITTEE
import com.todayeouido.tayapp.data.remote.dto.user.UserFavoriteCommitteeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserSettingApi {

    @POST(POST_USER_COMMITTEE)
    suspend fun postUserCommittee(
        @Body on: UserFavoriteCommitteeDto
    ): Response<UserFavoriteCommitteeDto>

    @GET(GET_USER_COMMITTEE)
    suspend fun getUserCommittee(): Response<UserFavoriteCommitteeDto>
}