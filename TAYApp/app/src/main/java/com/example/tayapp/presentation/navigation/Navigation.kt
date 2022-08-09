package com.example.tayapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tayapp.presentation.components.BottomBarTabs
import com.example.tayapp.presentation.screens.*
import com.example.tayapp.presentation.screens.Profile.*
import com.example.tayapp.presentation.states.TayAppState

@Composable
fun NavGraph(
    appState: TayAppState,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = MainDestination.SPLASH,
        modifier = Modifier.padding(innerPadding)
    ) {
        tayNavGraph(
            navController = appState.navController,
            upPress = appState::upPress
        )
    }
}

private fun NavGraphBuilder.tayNavGraph(
    navController: NavController,
    upPress: () -> Unit
) {
    /** nested Navigation */
    navigation(
        route = MainDestination.HOME,
        startDestination = BottomBarTabs.Feed.route
    ) {
        addHomeGraph(
            navController,
            upPress = upPress
        )
    }

    composable(
        route = MainDestination.SPLASH
    ) {
        SplashScreen(navController)
    }

    composable(
        route = MainDestination.LOGIN
    ) {
        LoginScreen(navController)
    }

    composable(
        route = MainDestination.SIGN_UP
    ){
        SignUpScreen(navController)
    }


    composable(
        route = MainDestination.DETAIL
    ) {

    }
}

fun NavGraphBuilder.addHomeGraph(
        navController: NavController,
        upPress: () -> Unit
) {
    composable(route = BottomBarTabs.Feed.route) { from ->
        Feed(navController = navController)
    }
    composable(BottomBarTabs.SCRAP.route) { from ->
        Scrap()
    }
    composable(BottomBarTabs.SEARCH.route) { from ->
        Search()
    }
    composable(BottomBarTabs.REPORT.route) {
        //Report(Modifier.fillMaxSize())
        BillDetail()
    }
    navigation(
        route = AppGraph.PROFILE_GRAPH,
        startDestination = BottomBarTabs.PROFILE.route
    ) {
        addProfileGraph(navController, upPress)
    }
}

fun NavGraphBuilder.addProfileGraph(
        navController: NavController,
        upPress: () -> Unit
){
    composable(route = BottomBarTabs.PROFILE.route) { from ->
        Profile(navController)
    }
    navigation(
        route = AppGraph.PROFILE_ACCOUNT_GRAPH,
        startDestination = ProfileDestination.ACCOUNT
    ) {
        addProfileAccountGraph(navController, upPress)
    }
    navigation(
        route = AppGraph.PROFILE_APPSETTING_GRAPH,
        startDestination = ProfileDestination.APPSETTING
    ) {
        addProfileAppSettingGraph(navController,upPress)
    }
    composable(ProfileDestination.INQUIRE) {
        //Report(Modifier.fillMaxSize())
        ProfileInquire(upPress)
    }
    composable(ProfileDestination.APPINFO) {
        ProfileAppInfo(upPress)
    }
}

fun NavGraphBuilder.addProfileAccountGraph(
        navController: NavController,
        upPress: () -> Unit
){
    composable(route = ProfileDestination.ACCOUNT) { from ->
        ProfileAccount(navController = navController, upPress)
    }
    composable(ProfileDestination.FAVORITE) { from ->
        ProfileFavoriteSetting(upPress)
    }
}


fun NavGraphBuilder.addProfileAppSettingGraph(
        navController: NavController,
        upPress: () -> Unit
){
    composable(route = ProfileDestination.APPSETTING) { from ->
        ProfileAppSetting(navController = navController, upPress)
    }
    composable(ProfileDestination.VISIBILITY) { from ->
        ProfileVisibility(upPress)
    }
    composable(ProfileDestination.ALARM) { from ->
        ProfileAlarm(upPress)
    }
}

