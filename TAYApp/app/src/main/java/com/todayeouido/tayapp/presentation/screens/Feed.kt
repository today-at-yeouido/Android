package com.todayeouido.tayapp.presentation.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.todayeouido.tayapp.domain.model.toDomain
import com.todayeouido.tayapp.presentation.components.*
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.viewmodels.FeedViewModel
import kotlinx.coroutines.launch


@Composable
fun Feed(
    onBillSelected: (Int) -> Unit,
    navigateToLogin: () -> Unit,
    navigateToFavorite: () -> Unit
) {

    val viewModel = hiltViewModel<FeedViewModel>()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isExpanded by viewModel.isExpanded.collectAsState()
    val mostViewed by viewModel.state.collectAsState()
    val recommendBill by viewModel.recommendBill.collectAsState()
    val recentBill by viewModel.recentBill.collectAsState()

    var dialogVisible by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as Activity)
    val scope = rememberCoroutineScope()

    val pagingLoading by viewModel.pagingLoading.collectAsState()
    val endReached by viewModel.endReached.collectAsState()

    AppFinishNoticeDialog(
        "종료하시겠습니까?",
        dialogVisible, {
        dialogVisible = !dialogVisible
    }) { activity.finish() }

    BackHandler(enabled = true) { // <-----
        scope.launch {
            dialogVisible = !dialogVisible
        }
    }




    Column() {
        TayHomeTopAppBar(
            onTagClick = viewModel::onCategorySelected,
            currentTag = selectedCategory!!,
            isExpanded = isExpanded!!,
            onArrowClick = viewModel::onExpandChange
        )
        if (UserState.network) {
            if (mostViewed.isLoading || (pagingLoading&&!endReached&&recentBill.size==0)) {
                LoadingView(modifier = Modifier.fillMaxSize())
            }


            Crossfade(targetState = selectedCategory) { it ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    itemsIndexed(recentBill) { index, item ->
                        if (index >= recentBill.size - 1 && !endReached && !pagingLoading) {
                            viewModel.tryGetRecentBill()
                        }
                        if (index == 0) {
                            if(selectedCategory != 0) {
                                CardCommitt(selectedCategory = selectedCategory)
                            }
                            CardMostViewed(items = mostViewed, onBillSelected)
                            Spacer(modifier = Modifier.height(40.dp))

                            if(selectedCategory == 0){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Title(
                                        "사용자 맞춤 추천법안",
                                        modifier = Modifier
                                            .padding(vertical = 0.dp, horizontal = KeyLine)
                                    )

                                }
                                CardsUser(
                                    onClick = onBillSelected,
                                    navigateToLogin = navigateToLogin,
                                    navigateToFavorite = navigateToFavorite,
                                    recommendBill = recommendBill
                                )
                                Spacer(modifier = Modifier.height(40.dp))
                            }
                            /**
                             * 최근 발의 법안
                             */
                            Title(
                                "최근 발의 법안",
                                modifier = Modifier
                                    .padding(vertical = 7.dp, horizontal = KeyLine)
                            )

                        }

                        CardBill(
                            modifier = Modifier.padding(horizontal = KeyLine),
                            bill = item!!.toDomain(),
                            onClick = onBillSelected
                        )

                    }

                    if(pagingLoading) {
                        item {
                            LoadingView(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
                it
            }
        } else {
            NetworkErrorScreen{
                UserState.network = true
                viewModel.tryGetMostViewed()
                viewModel.tryRecommendBill()
            }
        }
    }

    LaunchedEffect(key1 = UserState.network) {
        if (UserState.network) {
        }
    }

    //사용자 추천법안 refresh 버튼을 없애기 위해 생성
    LaunchedEffect(key1 = selectedCategory == 0){
        viewModel.tryRecommendBill()
    }
}





