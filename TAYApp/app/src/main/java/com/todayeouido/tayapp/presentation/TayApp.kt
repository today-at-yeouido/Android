package com.todayeouido.tayapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.todayeouido.tayapp.presentation.components.TayBottomBar
import com.todayeouido.tayapp.presentation.components.TayScaffold
import com.todayeouido.tayapp.presentation.navigation.NavGraph
import com.todayeouido.tayapp.presentation.states.rememberTayAppState
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
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