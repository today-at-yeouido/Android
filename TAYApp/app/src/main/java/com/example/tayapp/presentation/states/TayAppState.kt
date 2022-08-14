package com.example.tayapp.presentation.states

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tayapp.presentation.components.BottomBarTabs
import com.example.tayapp.presentation.navigation.AppGraph
import com.example.tayapp.presentation.navigation.Destinations
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberTayAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, coroutineScope) {
    TayAppState(scaffoldState, navController, coroutineScope)
}

@Stable
class TayAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    scope: CoroutineScope
) {
    //    로그인 체크
//    init {
//        scope.launch {
//
//        }
//    }
    val bottomBarTabs = BottomBarTabs.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    /** bottomBarsTabs
     * currentBackStackEntry가 변경될 때 마다 Recomposition 일어난다. */
    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    /** Recomposition으로 currentDestination이 현재 route가 됨 */
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(AppGraph.HOME_GRAPH) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToBillDetail(billId: Int, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${Destinations.DETAIL}/$billId")
        }
    }

    fun upPress() {
        navController.navigateUp()
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)