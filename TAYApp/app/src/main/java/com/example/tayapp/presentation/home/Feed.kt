package com.example.tayapp.presentation.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tayapp.presentation.components.CardNewsRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Feed(modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val items = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
    var cardIndex by remember { mutableStateOf(0) }

    Box {
        CardNewsRow(
            pagerState = pagerState,
            items = items,
            cardIndex = cardIndex,
        )
    }
}



