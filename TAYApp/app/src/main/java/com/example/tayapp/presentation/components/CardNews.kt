package com.example.tayapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayEmoji
import com.example.tayapp.presentation.utils.TayIcons

@Composable
fun CardNewsRow(
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    items: List<Int>,
    cardIndex: Int
) {
    Box() {
        LazyRow(
            modifier = modifier,
            state = scrollState,
            contentPadding = PaddingValues(horizontal = CardNews_KeyLine)
        ) {
            items(items.size) {
                CardNewsItem()
            }
        }
        NewsIndicator(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            itemSize = items.size,
            index = cardIndex
        )
    }
}


@Composable
private fun CardNewsItem(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .size(MainActivity.displayWidth - KeyLine.twice(), 280.dp)
            .background(color = lm_card_yellow, shape = CardNewsShape.large)
    ) {
        Column(modifier = modifier.padding(14.dp)) {
            Spacer(Modifier.height(25.dp))
            NewsTopContent()
        }
        NewsBottomContent()
    }
}

@Composable
private fun NewsBottomContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(color = lm_gray700, shape = CardNewsShape.large)
            .padding(top = 16.dp, start = 14.dp, end = 14.dp)
    ) {
        NewsCap(Modifier.padding(bottom = 6.dp))
        NewsHeaderItem()
        NewsHeaderItem()
    }
}

@Composable
private fun NewsCap(modifier: Modifier) {
    Row(modifier = modifier) {
        Image(
            imageVector = TayIcons.card_article,
            contentDescription = "",
            modifier = Modifier
                .padding(end = 4.dp)
                .size(16.dp),
            colorFilter = ColorFilter.tint(lm_gray000)
        )
        Text(
            text = "관련 뉴스",
            fontWeight = FontWeight.Medium,
            color = lm_gray000,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun NewsTopContent() {
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
        Text(
            text = TayEmoji.card_emoji,
            fontSize = 72.sp
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
            .background(color = lm_gray700, shape = CardNewsShape.medium)
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
            .background(color = lm_gray000, shape = CardNewsShape.medium)
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
            Surface(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                shape = CircleShape,
                color = if (i == index) lm_gray000 else Color.DarkGray

            ) {
                Box(modifier = Modifier.size(5.dp))
            }
        }
    }
}

@Composable
fun NewsHeaderItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "8시간 전 [더피알]",
            color = lm_gray200,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "중대재해처벌법, 두려움을 기회로 중대재해처벌법, 두려움을 기회로 중대재해처벌법, 두려움을 기회로",
            overflow = TextOverflow.Ellipsis,
            color = lm_gray050,
            maxLines = 1,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(2f)
        )

        Icon(
            imageVector = TayIcons.navigate_next,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .size(20.dp, 20.dp)
                .weight(0.5f)
        )
    }
}

private fun Dp.twice(): Dp = (2 * this.value).dp