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
        tayNavGraph(appState.navController)
    }
}

private fun NavGraphBuilder.tayNavGraph(navController: NavController) {
    /** nested Navigation */
    navigation(
        route = MainDestination.HOME,
        startDestination = BottomBarTabs.Feed.route
    ) {
        addHomeGraph(navController)
    }

    composable(
        route = MainDestination.SPLASH
    ) {
        SplashScreen(navController)
    }

    composable(
        route = MainDestination.LOGIN
    ) {
        LoginScreen()
    }

    composable(
        route = MainDestination.DETAIL
    ) {

    }
}

fun NavGraphBuilder.addHomeGraph(navController: NavController) {
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
        Report(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.PROFILE.route) {
        Profile(Modifier.fillMaxSize())
    }
}