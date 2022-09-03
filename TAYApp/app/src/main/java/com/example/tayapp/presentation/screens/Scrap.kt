package com.example.tayapp.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.navigation.GroupBillParcelableModel
import com.example.tayapp.presentation.states.ScrapState
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.viewmodels.ScrapViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun Scrap(
    onBillSelected: (Int) -> Unit,
    onGroupBillSelected: (Int, GroupBillParcelableModel) -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val viewModel = hiltViewModel<ScrapViewModel>()
    val scrapState by viewModel.scrapState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = true){
        viewModel.refresh()
    }
    
    
    TayScaffold(
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
        Column(
            modifier = Modifier.background(TayAppTheme.colors.background)
        ) {
            TayTopAppBar("스크랩")
            if (UserState.network) {
                if (UserState.isLogin()) {
                    if (scrapState.isLoading) {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    } else {
                        ScrapScreen(
                            isRefreshing,
                            viewModel,
                            scrapState,
                            onBillSelected,
                            coroutineScope,
                            scaffoldState,
                            scrollState,
                            onGroupBillSelected
                        )
                    }
                } else {
                    UnAuthorizeScreen {

                    }
                }
            } else {
                NetworkErrorScreen {
                    UserState.network = true
                    viewModel.refresh()
                }
            }
        }
    }
}

@Composable
private fun ScrapScreen(
    isRefreshing: Boolean,
    viewModel: ScrapViewModel,
    scrapState: ScrapState,
    onBillSelected: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    scrollState: LazyListState,
    onGroupBillSelected: (Int, GroupBillParcelableModel) -> Unit
) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = KeyLine),
            state = scrollState
        ) {
            items(scrapState.bill) { bill ->
                var _isBookMarked = remember { mutableStateOf(true) }

                if (bill.bills.size == 1) {
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
                                    SnackbarResult.ActionPerformed -> {
                                        _isBookMarked.value = true
                                    }
                                    SnackbarResult.Dismissed -> viewModel.deleteScrap(
                                        bill.bills.first().id
                                    )
                                }
                            }
                        }
                    )
                } else {
                    CardMultiple(
                        bill = bill,
                        onLineClick = onBillSelected,
                        keyword = "",
                        onButtonClick = onGroupBillSelected
                    )
                }
            }
        }
    }
}
