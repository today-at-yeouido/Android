package com.example.tayapp.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tayapp.presentation.components.TayBottomBar
import com.example.tayapp.presentation.components.TayScaffold
import com.example.tayapp.presentation.navigation.NavGraph
import com.example.tayapp.presentation.states.ConnectionState
import com.example.tayapp.presentation.states.ConnectivityState
import com.example.tayapp.presentation.states.rememberTayAppState
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TayApp() {
    TayAppTheme {
        val appState = rememberTayAppState()
        TayScaffold(
            modifier = Modifier,
            scaffoldState = appState.scaffoldState,
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