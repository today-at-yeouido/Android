package com.example.tayapp.presentation.navigation

import android.opengl.Visibility


/** Destination 임시로 HOME, DETAIL 설정 */
object MainDestination {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val DETAIL = "detail"
    const val SIGN_UP = "signup"
}

object BottomBarDestination {
    const val FEED = "home/feed"
    const val SCRAP = "home/scrap"
    const val SEARCH = "home/search"
    const val REPORT = "home/report"
    const val PROFILE = "home/profile"
}

object ProfileDestination {
    const val ACCOUNT = "home/profile/account"
    const val APPSETTING = "home/profile/appsetting"
    const val INQUIRE = "home/profile/inquire"
    const val APPINFO = "home/profile/appinfo"
    const val FAVORITE = "home/profile/account/favorite"
    const val VISIBILITY = "home/profile/appsetting/visibility"
    const val ALARM = "home/profile/appsetting/alarm"
}

object AppGraph {
    const val PROFILE_GRAPH = "profile_graph"
    const val PROFILE_ACCOUNT_GRAPH = "profile_account_graph"
    const val PROFILE_APPSETTING_GRAPH = "profile_appsetting_graph"
}