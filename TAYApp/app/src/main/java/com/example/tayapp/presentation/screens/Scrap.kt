package com.example.tayapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.ScrapViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun Scrap(
    list: List<String> = listOf("1", "2", "3"),
    onBillSelected: (Int) -> Unit
) {

    val viewModel = hiltViewModel<ScrapViewModel>()
    val scrapState by viewModel.scrapState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    Column {
        TayTopAppBar("스크랩")
        if (UserState.network) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.refresh() },
            ) {
                if (UserState.isLogin())
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = KeyLine, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(if (list.isEmpty()) 10.dp else 16.dp)
                    ) {
                        items(scrapState.bill) {
                            CardSearch(
                                bill = it,
                                onBillSelected = onBillSelected
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
}
