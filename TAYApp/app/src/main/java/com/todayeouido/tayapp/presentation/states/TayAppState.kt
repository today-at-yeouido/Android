package com.todayeouido.tayapp.presentation.states

import android.net.Uri
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
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.todayeouido.tayapp.presentation.components.BottomBarTabs
import com.todayeouido.tayapp.presentation.navigation.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberTayAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(scaffoldState, navController, coroutineScope) {
    TayAppState(scaffoldState, navController, coroutineScope)
}

@Stable
class TayAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    scope: CoroutineScope,
) {

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
                popUpTo(BottomBarDestination.FEED) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToBillDetail(billId: Int, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${AppGraph.DETAIL_GRAPH}/$billId")
        }
    }

    fun navigateToGroupBill(groupId: Int, from: NavBackStackEntry) {

        if (from.lifecycleIsResumed()) {
            navController.navigate("groupID/$groupId")
        }
    }

    fun navigateToGroupBillScrap(groupId: Int, billList: GroupBillParcelableModel, from: NavBackStackEntry) {

        val groupBill = Uri.encode(Gson().toJson(billList))
        if (from.lifecycleIsResumed()) {
            navController.navigate("groupID/$groupId/${groupBill}")
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