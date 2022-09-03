package com.example.tayapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.example.tayapp.presentation.components.BottomBarTabs
import com.example.tayapp.presentation.screens.*
import com.example.tayapp.presentation.screens.Profile.*
import com.example.tayapp.presentation.screens.initial.LoginScreen
import com.example.tayapp.presentation.screens.initial.RegisterScreen
import com.example.tayapp.presentation.screens.initial.SplashScreen
import com.example.tayapp.presentation.screens.search.Search
import com.example.tayapp.presentation.states.TayAppState
import com.example.tayapp.presentation.viewmodels.LoginViewModel

@Composable
fun NavGraph(
    appState: TayAppState, innerPadding: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = AppGraph.INITIAL_GRAPH,
        modifier = Modifier.padding(innerPadding)
    ) {
        tayNavGraph(
            navController = appState.navController,
            upPress = appState::upPress,
            appState = appState,
            onBillSelected = appState::navigateToBillDetail,
            onGroupBillSelected = appState::navigateToGroupBill,
            onGroupBillScrapSelected = appState::navigateToGroupBillScrap
        )
    }
}

private fun NavGraphBuilder.tayNavGraph(
    navController: NavController,
    upPress: () -> Unit,
    appState: TayAppState,
    onBillSelected: (Int, NavBackStackEntry) -> Unit,
    onGroupBillSelected: (Int, NavBackStackEntry) -> Unit,
    onGroupBillScrapSelected: (Int, GroupBillParcelableModel, NavBackStackEntry) -> Unit
) {
    initialNavigation(appState)

    /** nested Navigation */
    homeNavigation(navController, upPress, onBillSelected, onGroupBillSelected, onGroupBillScrapSelected)

    detailNavigation(upPress, onGroupBillSelected)

    groupBillScrapNavigation(upPress, onBillSelected)

    groupBillNavigation(upPress, onBillSelected)
}

private fun NavGraphBuilder.detailNavigation(
    upPress: () -> Unit,
    onGroupBillSelected: (Int, NavBackStackEntry) -> Unit
) {
    composable(
        "${Destinations.DETAIL}/{${Destinations.BILL_ID}}",
        arguments = listOf(navArgument(Destinations.BILL_ID) { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val billId = arguments.getInt(Destinations.BILL_ID)
        BillDetail(billId = billId, upPress, onGroupBillSelected = {})
    }
}

private fun NavGraphBuilder.groupBillScrapNavigation(
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit
) {
    composable(
        "groupID/{${Destinations.GROUP_ID}}/{${GROUP_BILL}}",
        arguments = listOf(
            navArgument(Destinations.GROUP_ID) { type = NavType.IntType },
            navArgument(GROUP_BILL){ type = GroupBillParcelableModel.NavigationType}
        )
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val groupBill = arguments.getParcelable<GroupBillParcelableModel>(GROUP_BILL)!!

        GroupBill(upPress, onBillSelected = {id -> onBillSelected(id, backStackEntry)}, groupBill.BillList)
    }
}

private fun NavGraphBuilder.groupBillNavigation(
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit
) {
    composable(
        "groupID/{${Destinations.GROUP_ID}}",
        arguments = listOf(navArgument(Destinations.GROUP_ID) { type = NavType.IntType })
    ) { backStackEntry ->

        GroupBill(upPress, onBillSelected = {id -> onBillSelected(id, backStackEntry)})
    }
}

private fun NavGraphBuilder.initialNavigation(appState: TayAppState) {
    navigation(
        route = AppGraph.INITIAL_GRAPH, startDestination = Destinations.SPLASH
    ) {
        initialGraph(appState.navController)
    }
}

private fun NavGraphBuilder.homeNavigation(
    navController: NavController,
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit,
    onGroupBillSelected: (Int, NavBackStackEntry) -> Unit,
    onGroupBillScrapSelected: (Int,GroupBillParcelableModel, NavBackStackEntry) -> Unit

) {
    navigation(
        route = AppGraph.HOME_GRAPH, startDestination = BottomBarTabs.Feed.route
    ) {
        homeGraph(
            navController,
            upPress = upPress,
            onBillSelected = onBillSelected,
            onGroupBillSelected = onGroupBillSelected,
            onGroupBillScrapSelected = onGroupBillScrapSelected
        )
    }
}

private fun NavGraphBuilder.initialGraph(navController: NavController) {
    composable(
        route = Destinations.SPLASH
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(AppGraph.INITIAL_GRAPH)
        }
        val viewModel = hiltViewModel<LoginViewModel>(parentEntry)
        SplashScreen(navController, viewModel)
    }

    composable(
        route = Destinations.LOGIN
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(AppGraph.INITIAL_GRAPH)
        }
        val viewModel = hiltViewModel<LoginViewModel>(parentEntry)
        LoginScreen(navController, viewModel)
    }

    composable(
        route = Destinations.SIGN_UP
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(AppGraph.INITIAL_GRAPH)
        }
        val viewModel = hiltViewModel<LoginViewModel>(parentEntry)
        RegisterScreen(navController, viewModel)
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    upPress: () -> Unit,
    onBillSelected: (Int, NavBackStackEntry) -> Unit,
    onGroupBillSelected: (Int, NavBackStackEntry) -> Unit,
    onGroupBillScrapSelected: (Int,GroupBillParcelableModel, NavBackStackEntry) -> Unit
) {
    composable(route = BottomBarTabs.Feed.route) { from ->
        Feed(
            onBillSelected = { id -> onBillSelected(id, from) },
            navigateToLogin = { navController.navigate(Destinations.LOGIN) },
            navigateToFavorite = { navController.navigate(ProfileDestination.FAVORITE) }
        )
    }
    composable(BottomBarTabs.SCRAP.route) { from ->
        Scrap(
            onBillSelected = { id -> onBillSelected(id, from) },
            onGroupBillScrapSelected = {id, list -> onGroupBillScrapSelected(id, list, from)}
        )
    }
    composable(BottomBarTabs.SEARCH.route) { from ->
        Search(
            onBillSelected = { id -> onBillSelected(id, from) },
            onGroupBillSelected = {id -> onGroupBillSelected(id,  from)}
        )
    }

    navigation(
        route = AppGraph.PROFILE_GRAPH, startDestination = BottomBarDestination.PROFILE
    ) {
        addProfileGraph(navController, upPress)
    }
}

fun NavGraphBuilder.addProfileGraph(
    navController: NavController, upPress: () -> Unit
) {
    composable(route = BottomBarDestination.PROFILE) { from ->
        Profile(navController)
    }
    navigation(
        route = AppGraph.PROFILE_ACCOUNT_GRAPH, startDestination = ProfileDestination.ACCOUNT
    ) {
        addProfileAccountGraph(navController, upPress)
    }
    navigation(
        route = AppGraph.PROFILE_APPSETTING_GRAPH, startDestination = ProfileDestination.APPSETTING
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
    navController: NavController, upPress: () -> Unit
) {
    composable(route = ProfileDestination.ACCOUNT) { from ->
        ProfileAccount(navController = navController, upPress)
    }
    composable(ProfileDestination.FAVORITE) { from ->
        ProfileFavoriteSetting(upPress)
    }
}


fun NavGraphBuilder.addProfileAppSettingGraph(
    navController: NavController, upPress: () -> Unit
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

