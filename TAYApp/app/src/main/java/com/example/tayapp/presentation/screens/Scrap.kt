package com.example.tayapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.ScrapViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
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
        scaffoldState = scaffoldState
    ) {
        Column {
            TayTopAppBar("스크랩")
            if (UserState.network) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { viewModel.refresh() },
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
                                                    message = "asdf",
                                                    actionLabel = "asdf"
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
