package com.example.tayapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.viewmodels.ScrapViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch


@Composable
fun Scrap(
    list: List<String> = listOf("1", "2", "3"),
    onBillSelected: (Int) -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val viewModel = hiltViewModel<ScrapViewModel>()
    val scrapState by viewModel.scrapState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it,
                snackbar = {
                    TaySnackbar(
                        snackbarData = it,
                        imageVector = Icons.Default.QuestionMark,
                        imageColor = TayAppTheme.colors.information1
                    )
                }
            )
        }
    ) {
        Column {
            TayTopAppBar("스크랩")
            if (UserState.network) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { viewModel.refresh() },
                    refreshTriggerDistance = 50.dp,
                    indicator = { state, trigger ->
                        SwipeRefreshIndicator(
                            // Pass the SwipeRefreshState + trigger through
                            state = state,
                            refreshTriggerDistance = trigger,
                            // Enable the scale animation
                            scale = true,
                            // Change the color and shape
                            backgroundColor = TayAppTheme.colors.background,
                            shape = CircleShape,
                        )
                    }
                ) {
                    if (UserState.isLogin())
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(horizontal = KeyLine, vertical = 20.dp)
                        ) {
                            items(scrapState.bill) {
                                    bill ->
                                var _isBookMarked = remember{mutableStateOf(true)}

                                CardBillWithScrap(
                                    bill = bill,
                                    onBillSelected = onBillSelected,
                                    _isBookMarked = _isBookMarked.value,
                                    onScrapClickClicked = {
                                        _isBookMarked.value = false
                                        coroutineScope.launch {
                                            val snackbarResult =
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "",
                                                    actionLabel = "스크랩을 취소하시겠습니까?"
                                                )
                                            when (snackbarResult) {
                                                SnackbarResult.ActionPerformed -> {_isBookMarked.value = true}
                                                SnackbarResult.Dismissed -> viewModel.deleteScrap(bill.id)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                }
            } else {
                NetworkErrorScreen {
                    UserState.network = true
                    viewModel.refresh()
                }
            }
        }
        it
    }
}
