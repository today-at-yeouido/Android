package com.example.tayapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        startDestination = MainDestination.HOME,
        modifier = Modifier.padding(innerPadding)
    ) {
        tayNavGraph()
    }
}

private fun NavGraphBuilder.tayNavGraph() {
    /** nested Navigation */
    navigation(
        route = MainDestination.HOME,
        startDestination = BottomBarTabs.Feed.route
    ) {
        addHomeGraph()
    }

    composable(
        route = MainDestination.LOGIN
    ){
        LoginScreen()
    }

    composable(
        route = MainDestination.DETAIL
    ) {}
}

fun NavGraphBuilder.addHomeGraph() {
    composable(route = BottomBarTabs.Feed.route) { from ->
        Feed()
    }
    composable(BottomBarTabs.SCRAP.route) { from ->
        Search(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.SEARCH.route) { from ->
        Cart(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.REPORT.route) {
        Report(Modifier.fillMaxSize())
    }
    composable(BottomBarTabs.PROFILE.route) {
        Profile(Modifier.fillMaxSize())
    }
}