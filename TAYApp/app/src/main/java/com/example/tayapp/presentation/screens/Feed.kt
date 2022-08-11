package com.example.tayapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.viewmodels.HomeViewModel


@Composable
fun Feed(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBillSelected: (Int) -> Unit
) {

    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<HomeViewModel>()
    val mostViewed by viewModel.state.collectAsState()
    val userItems = listOf<Int>(1, 2, 3)
    val recentBill = viewModel.recentBill.collectAsLazyPagingItems()

    Column() {
        TayHomeTopAppBar()

        if(recentBill.loadState.refresh == LoadState.Loading){
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            itemsIndexed(items = recentBill) { index, item->
                if(index == 0){
                    CardMostViewed(items = mostViewed.bill)
                    Spacer(modifier = Modifier.height(40.dp))

                    Title(
                        "사용자 맞춤 추천법안",
                        modifier = Modifier
                            .padding(vertical = 0.dp, horizontal = KeyLine)
                    )
                    CardsUser(onClick = onBillSelected)
                    Spacer(modifier = Modifier.height(40.dp))

                    Title(
                        "최근 발의 법안",
                        modifier = Modifier
                            .padding(vertical = 7.dp, horizontal = KeyLine)
                    )
                }

                CardBill(
                    modifier = Modifier.padding(horizontal = KeyLine),
                    bill = item!!,
                    onClick = onBillSelected
                )

            }


        }
    }
}





