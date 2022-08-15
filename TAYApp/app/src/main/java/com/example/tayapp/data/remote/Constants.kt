package com.example.tayapp.data.remote

object Constants {
    const val BASE_URL = "http://todayeouido.co.kr/yeouido/v0/"
    const val AUTHORIZATION = "Authorization"

    const val POST_REGISTRATION = "account/registration/"
    const val POST_LOGIN = "account/login/"
    const val POST_LOGOUT = "account/logout/"
    const val POST_JWT_REFRESH = "account/token/refresh/"

    const val GET_BILL_DETAIL = "bill/detail/"
    const val GET_BILL_HOME = "bill/home/"
    const val GET_BILL_MOST_VIEWED = "bill/test/most-view/"
    const val GET_BILL_RECENT = "bill/test/recent-create/"
    const val GET_BILL_USER_RECOMMENDED = "bill/test/recommend/"
    const val GET_BILL_USER_RECENT_VIEWED = "bill/test/user-recent-view/"
    const val GET_BILL_SEARCH = "bill/test/search/"
}
