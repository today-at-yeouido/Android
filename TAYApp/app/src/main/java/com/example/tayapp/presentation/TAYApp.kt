package com.example.tayapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.tayapp.presentation.components.TayBottomBar
import com.example.tayapp.presentation.components.TayScaffold
import com.example.tayapp.presentation.home.HomeScreen
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun TayApp() {
    TayAppTheme {
        val appState = rememberTayAppState()
        TayScaffold(
            modifier = Modifier,
            scaffoldState = appState.scaffoldState,
            topBar = {},
            bottomBar = {
                TayBottomBar(tabs = appState.bottomBarTabs)
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
    composable(
        route = MainDestination.HOME
    ) { entry ->
        HomeScreen()
    }

    composable(
        route = MainDestination.DETAIL
    ) { entry ->

    }
}