package com.example.tayapp.presentation.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.itemsIndexed
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.viewmodels.FeedViewModel
import kotlinx.coroutines.launch


@Composable
fun Feed(
    onBillSelected: (Int) -> Unit,
    navigateToLogin: () -> Unit
) {

    val viewModel = hiltViewModel<FeedViewModel>()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isExpanded by viewModel.isExpanded.collectAsState()
    val mostViewed by viewModel.state.collectAsState()
    val recommendBill by viewModel.recommendBill.collectAsState()
    val recentBill = viewModel.recentBill.collectAsLazyPagingItems()

    var dialogVisible by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as Activity)
    val scope = rememberCoroutineScope()

    AppFinishNoticeDialog(dialogVisible, {
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
            if (recentBill.loadState.refresh == LoadState.Loading || mostViewed.isLoading) {
                LoadingView(modifier = Modifier.fillMaxSize())
            }


            Crossfade(targetState = selectedCategory) { it ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    itemsIndexed(items = recentBill) { index, item ->
                        if (index == 0) {
                            CardMostViewed(items = mostViewed)
                            Spacer(modifier = Modifier.height(40.dp))

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
                                IconButton(
                                    onClick = viewModel::tryRecommendBill,
                                    modifier = Modifier
                                        .padding(end = KeyLine)
                                        .size(40.dp)
                                        .border(1.dp, TayAppTheme.colors.border, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "refresh",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            CardsUser(
                                onClick = onBillSelected,
                                navigateToLogin = navigateToLogin,
                                recommendBill = recommendBill
                            )
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
            recentBill.retry()
        }
    }
}





