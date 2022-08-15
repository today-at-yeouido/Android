package com.example.tayapp.presentation.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.states.LoginState
import com.example.tayapp.presentation.viewmodels.HomeViewModel
import com.example.tayapp.presentation.viewmodels.FeedViewModel


@Composable
fun Feed(
    navController: NavController,
    onBillSelected: (Int) -> Unit,
) {

    val viewModel = hiltViewModel<FeedViewModel>()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isExpanded by viewModel.isExpanded.collectAsState()
    val mostViewed by viewModel.state.collectAsState()
    val recentBill = viewModel.recentBill.collectAsLazyPagingItems()

    Column() {
        TayHomeTopAppBar(
            onTagClick = viewModel::onCategorySelected,
            currentTag = selectedCategory!!,
            isExpanded = isExpanded!!,
            onArrowClick = viewModel::onExpandChange
        )

        if(recentBill.loadState.refresh == LoadState.Loading){
            LoadingView(modifier = Modifier.fillMaxSize())
        }


        Crossfade(targetState = selectedCategory) {it ->
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
                            "최근 발의 법안 ${it}",
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
}





