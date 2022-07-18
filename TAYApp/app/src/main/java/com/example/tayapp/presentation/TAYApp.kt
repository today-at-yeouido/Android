package com.example.tayapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.tayapp.presentation.components.TAYScaffold
import com.example.tayapp.presentation.home.HomeScreen
import com.example.tayapp.presentation.ui.theme.TAYAppTheme

@Composable
fun TAYApp() {
    TAYAppTheme {
        val appState = rememberTAYAppState()
        TAYScaffold(
            modifier = Modifier,
            scaffoldState = appState.scaffoldState,
            topBar = {},
            bottomBar = {},
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