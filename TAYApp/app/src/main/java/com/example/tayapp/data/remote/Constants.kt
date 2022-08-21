package com.example.tayapp.data.remote

object Constants {
    const val BASE_URL = "http://todayeouido.co.kr/yeouido/v0/"
    const val GOOGLE_URL = "https://www.googleapis.com/"
    const val AUTHORIZATION = "Authorization"

    const val POST_REGISTRATION = "account/registration/"
    const val POST_LOGIN = "account/login/"
    const val POST_LOGOUT = "account/logout/"
    const val POST_JWT_REFRESH = "account/token/refresh/"
    const val POST_SOCIAL_LOGIN = "user/kakao/login/manage/"

    const val GET_BILL_DETAIL = "bill/detail/"
    const val GET_BILL_HOME = "bill/home/"
    const val GET_BILL_MOST_VIEWED = "bill/test/most-view/"
    const val GET_BILL_RECENT = "bill/test/recent-create/"
    const val GET_BILL_USER_RECOMMENDED = "bill/test/recommend/"
    const val GET_BILL_USER_RECENT_VIEWED = "bill/test/user-recent-view/"
    const val GET_BILL_SEARCH = "bill/search/"

    const val POST_ADD_SCRAP ="bill/scrap/add/"
    const val POST_DELETE_SCRAP ="bill/scrap/delete/"
    const val GET_BILL_SCRAP = "bill/scrap/"
}
