package com.example.tayapp.presentation.navigation


/** Destination 임시로 HOME, DETAIL 설정 */
object Destinations {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val DETAIL = "detail"
    const val SIGN_UP = "signup"
    const val BILL_ID = "billId"
    const val GROUP_ID = "groupId"
}

object BottomBarDestination {
    const val FEED = "home/feed"
    const val SCRAP = "home/scrap"
    const val SEARCH = "home/search"
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
    const val HOME_GRAPH = "home_graph"
    const val INITIAL_GRAPH = "initial_graph"
    const val PROFILE_GRAPH = "profile_graph"
    const val PROFILE_ACCOUNT_GRAPH = "profile_account_graph"
    const val PROFILE_APPSETTING_GRAPH = "profile_appsetting_graph"
}