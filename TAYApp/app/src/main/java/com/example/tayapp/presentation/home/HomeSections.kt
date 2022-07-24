package com.example.tayapp.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.ui.theme.*


@Composable
fun Feed(modifier: Modifier) {

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val items = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
    var cardIndex by remember { mutableStateOf(0) }

    Box {
        CardNewsRow(scrollState, items = items, cardIndex = cardIndex)
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

@Composable
private fun CardNewsRow(
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    items: List<Int>,
    cardIndex: Int
) {
    Box(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Green),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = KeyLine)
        ) {
            itemsIndexed(items) { item, index ->
                CardNewsItem(index)
            }
        }
        NewsIndicator(Modifier.align(alignment = Alignment.TopCenter), items.size, cardIndex)
    }
}

private fun getCardPosition(scrollState: LazyListState): Int {
    return scrollState.layoutInfo.visibleItemsInfo[0].size / 2 - scrollState.firstVisibleItemScrollOffset
}

@Composable
private fun CardNewsItem(index: Int) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(MainActivity.displayWidth - 32.dp, 280.dp)
            .background(color = card_yellow, shape = RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Spacer(Modifier.height(25.dp))
            NewsTopContnet()
        }
        NewsBottomContent()
    }
}

@Composable
private fun NewsBottomContent() {
    Row(
        modifier = Modifier
            .background(color = lm_gray700, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "관련 뉴스",
                fontWeight = FontWeight.Medium,
                color = lm_gray000,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(6.dp))
            NewsHeaderItem()
            NewsHeaderItem()
        }
    }
}

@Composable
private fun NewsTopContnet() {
    Row {
        Column {
            NewsLabel()
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "중대재해처벌법 개정안",
                color = lm_gray000,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "국회 본회의 통과",
                color = lm_gray000,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(9.dp))
            Text(
                "제적의원 2/3이 찬성, 압도적 찬성 속 통과",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = lm_gray050
            )
            Spacer(Modifier.height(6.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "",
            modifier = Modifier.size(72.dp)
        )
    }
}

@Composable
private fun NewsLabel() {
    Row {
        NewsLabelIcon()
        NewsLabelIcon2()
    }
}

@Composable
fun NewsLabelIcon() {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .background(color = lm_gray700, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Text(text = "개정안", color = lm_gray000, fontWeight = FontWeight.Normal, fontSize = 12.sp)
    }
}

@Composable
fun NewsLabelIcon2() {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .background(color = lm_gray000, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Text(text = "접수", color = lm_gray700, fontWeight = FontWeight.Normal, fontSize = 12.sp)
    }
}


@Composable
fun NewsIndicator(modifier: Modifier = Modifier, itemSize: Int, index: Int) {
    Row(
        modifier = modifier
            .padding(top = 14.dp)
    ) {
        for (i in 0 until itemSize) {
            Log.d("##99", "index:$index")
            Surface(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                shape = CircleShape,
                color = if (i == index) lm_gray000 else Color.DarkGray

            ) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                )
            }

        }
    }
}

@Composable
fun NewsHeaderItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "8시간 전 [더피알]",
            color = lm_gray200,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
//        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "중대재해처벌법, 두려움을 기회로 중대재해처벌법, 두려움을 기회로 중대재해처벌법, 두려움을 기회로",
            overflow = TextOverflow.Ellipsis,
            color = lm_gray050,
            maxLines = 1,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(2f)
        )
//        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Outlined.ArrowRight,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .size(20.dp, 20.dp)
                .weight(0.5f)
        )
    }
}


@Composable
fun Search(modifier: Modifier) {
    Text(text = "Search", modifier = modifier)
}

@Composable
fun Cart(modifier: Modifier) {
    Text(text = "Cart", modifier = modifier)
}

@Composable
fun Report(modifier: Modifier) {
    Text(text = "Report", modifier = modifier)
}

@Composable
fun Profile(modifier: Modifier) {
    Text(text = "Profile", modifier = modifier)
}