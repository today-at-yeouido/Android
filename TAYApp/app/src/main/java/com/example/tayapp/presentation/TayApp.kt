package com.example.tayapp.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.navigation.MainDestination
import com.example.tayapp.presentation.navigation.NavGraph
import com.example.tayapp.presentation.states.TayAppState
import com.example.tayapp.presentation.states.rememberTayAppState
import com.example.tayapp.presentation.ui.theme.TayAppTheme

@Composable
fun TayApp() {
    TayAppTheme {
        val appState = rememberTayAppState()
        TayScaffold(
            modifier = Modifier,
            scaffoldState = appState.scaffoldState,
            topBar = { TayHomeTopAppBar() },
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
            NavGraph(appState, innerPadding)
        }
    }
}