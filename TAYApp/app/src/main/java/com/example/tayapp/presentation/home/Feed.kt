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


@Composable
fun Feed(modifier: Modifier = Modifier) {

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val items = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
    var cardIndex by remember { mutableStateOf(0) }

    Box {
        CardNewsRow(
            scrollState = scrollState,
            items = items,
            cardIndex = cardIndex,
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }

    LaunchedEffect(key1 = scrollState.isScrollInProgress) {
        val itemIndex = scrollState.firstVisibleItemIndex
        val itemOffset = scrollState.firstVisibleItemScrollOffset

        if (itemOffset != 0) {
            if (getCardPosition(scrollState) > 0) {
                scrollState.animateScrollToItem(itemIndex)
            } else {
                if (!(itemIndex == items.size - 2 && getCardPosition(scrollState) < 0)
                ) {
                    scrollState.animateScrollToItem(itemIndex + 1)
                }
            }
        }
        cardIndex = if (itemIndex == items.size - 2 && getCardPosition(scrollState) < 0)
            scrollState.firstVisibleItemIndex + 1 else scrollState.firstVisibleItemIndex
    }
}

private fun getCardPosition(scrollState: LazyListState): Int {
    return scrollState.layoutInfo.visibleItemsInfo[0].size / 2 - scrollState.firstVisibleItemScrollOffset
}


