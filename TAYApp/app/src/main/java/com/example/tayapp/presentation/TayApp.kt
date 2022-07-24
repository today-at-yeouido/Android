package com.example.tayapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.navigation.MainDestination
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun TayApp() {
    TayAppTheme {
        val appState = rememberTayAppState()
        TayScaffold(
            modifier = Modifier,
            scaffoldState = appState.scaffoldState,
            topBar = { TayHomeTopAppBar()},
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    TayBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute,
                        navigateToRoute = appState::navigateToBottomBarRoute
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestination.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {
                tayNavGraph()
            }
        }
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
        route = MainDestination.DETAIL
    ){}
}