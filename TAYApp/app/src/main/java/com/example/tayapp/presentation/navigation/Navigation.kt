package com.example.tayapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tayapp.presentation.components.BottomBarTabs
import com.example.tayapp.presentation.screens.*
import com.example.tayapp.presentation.screens.Profile.*
import com.example.tayapp.presentation.states.TayAppState

@Composable
fun NavGraph(
    appState: TayAppState,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = AppGraph.INITIAL_GRAPH,
        modifier = Modifier.padding(innerPadding)
    ) {
        tayNavGraph(
            navController = appState.navController,
            upPress = appState::upPress,
            onBillSelected = appState::navigateToBillDetail
        )
    }
}

private fun NavGraphBuilder.tayNavGraph(
    navController: NavController,
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit
) {
    /** nested Navigation */
    homeNavigation(navController, upPress, onBillSelected)

    initialNavigation(navController)

    detailNavigation(upPress)
}

private fun NavGraphBuilder.detailNavigation(upPress: () -> Unit) {
    composable(
        "${Destinations.DETAIL}/{${Destinations.BILL_ID}}",
        arguments = listOf(navArgument(Destinations.BILL_ID) { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val billId = arguments.getInt(Destinations.BILL_ID)
        BillDetail(billId = billId, upPress)
    }
}

private fun NavGraphBuilder.initialNavigation(navController: NavController) {
    navigation(
        route = AppGraph.INITIAL_GRAPH,
        startDestination = Destinations.SPLASH
    ) {
        initialGraph(navController)
    }
}

private fun NavGraphBuilder.homeNavigation(
    navController: NavController,
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit
) {
    navigation(
        route = AppGraph.HOME_GRAPH,
        startDestination = BottomBarTabs.Feed.route
    ) {
        homeGraph(
            navController,
            upPress = upPress,
            onBillSelected = onBillSelected
        )
    }
}

private fun NavGraphBuilder.initialGraph(navController: NavController) {
    composable(
        route = Destinations.SPLASH
    ) {
        SplashScreen(navController)
    }

    composable(
        route = Destinations.LOGIN
    ) {
        LoginScreen(navController)
    }

    composable(
        route = Destinations.SIGN_UP
    ) {
        SignUpScreen(navController)
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit
) {
    composable(route = BottomBarTabs.Feed.route) { from ->
        Feed(navController = navController, onBillSelected = { id -> onBillSelected(id, from) })
    }
    composable(BottomBarTabs.SCRAP.route) { from ->
        Scrap(onBillSelected = { id -> onBillSelected(id, from) })
    }
    composable(BottomBarTabs.SEARCH.route) { from ->
        Search(onBillSelected = { id -> onBillSelected(id, from) })
    }
    composable(BottomBarTabs.REPORT.route) {
        //Report(Modifier.fillMaxSize())
        //BillDetail()
    }
    navigation(
        route = AppGraph.PROFILE_GRAPH,
        startDestination = BottomBarDestination.PROFILE
    ) {
        addProfileGraph(navController, upPress)
    }
}

fun NavGraphBuilder.addProfileGraph(
    navController: NavController,
    upPress: () -> Unit
) {
    composable(route = BottomBarDestination.PROFILE) { from ->
        Profile(navController)
    }
    navigation(
        route = AppGraph.PROFILE_ACCOUNT_GRAPH,
        startDestination = ProfileDestination.ACCOUNT
    ) {
        addProfileAccountGraph(navController, upPress)
    }
    navigation(
        route = AppGraph.PROFILE_APPSETTING_GRAPH,
        startDestination = ProfileDestination.APPSETTING
    ) {
        addProfileAppSettingGraph(navController, upPress)
    }
    composable(ProfileDestination.INQUIRE) {
        //Report(Modifier.fillMaxSize())
        ProfileInquire(upPress)
    }
    composable(ProfileDestination.APPINFO) {
        ProfileAppInfo(upPress)
    }
}

fun NavGraphBuilder.addProfileAccountGraph(
    navController: NavController,
    upPress: () -> Unit
) {
    composable(route = ProfileDestination.ACCOUNT) { from ->
        ProfileAccount(navController = navController, upPress)
    }
    composable(ProfileDestination.FAVORITE) { from ->
        ProfileFavoriteSetting(upPress)
    }
}


fun NavGraphBuilder.addProfileAppSettingGraph(
    navController: NavController,
    upPress: () -> Unit
) {
    composable(route = ProfileDestination.APPSETTING) { from ->
        ProfileAppSetting(navController = navController, upPress)
    }
    composable(ProfileDestination.VISIBILITY) { from ->
        ProfileVisibility(upPress)
    }
    composable(ProfileDestination.ALARM) { from ->
        ProfileAlarm(upPress)
    }
}

