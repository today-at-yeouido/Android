@file:OptIn(ExperimentalPagerApi::class)

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayEmoji
import com.example.tayapp.presentation.utils.TayIcons
import com.google.accompanist.pager.*

@Composable
fun CardNewsRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    items: List<Int>,
    cardIndex: Int
) {

    Box() {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .zIndex(0.5f)
                .align(Alignment.TopCenter)
                .padding(top = 14.dp),
            spacing = CardNews_Indicator_Spacing,
            indicatorHeight = 6.dp,
            indicatorWidth = 6.dp,
            indicatorShape = CircleShape,
            activeColor = lm_gray000,
        )
        HorizontalPager(
            count = items.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = CardNews_KeyLine)
        ) {
            CardNewsItem()
        }
    }
}

@Preview
@Composable
private fun CardNewsItem(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .size(MainActivity.displayWidth - KeyLine.twice(), 280.dp)
            .background(color = lm_card_yellow, shape = CardNewsShape.large)
    ) {
        Spacer(Modifier.height(40.dp))
        Column(
            modifier = modifier
                .padding(horizontal = 14.dp)
                .height(118.dp)
                .fillMaxWidth()
                .background(color = Color.Blue)
        ) {
            NewsTopContent()
        }
        Spacer(Modifier.height(12.dp))
        NewsBottomContent()
    }
}

@Preview
@Composable
private fun NewsBottomContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(110.dp)
            .background(color = lm_gray700, shape = CardNewsShape.large)
            .padding(14.dp)
    ) {
        NewsCap(Modifier.padding(bottom = 5.dp))
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

@Preview
@Composable
private fun NewsTopContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
//            NewsLabel()
            Spacer(modifier = Modifier.height(43.dp))
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
            Spacer(Modifier.height(7.dp))
            Text(
                "제적의원 2/3이 찬성, 압도적 찬성 속 통과",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = lm_gray050
            )
        }
        Text(
            text = TayEmoji.card_emoji,
            fontSize = 72.sp
        )
    }
}

@Composable
private fun NewsLabel() {
    Row() {
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
fun NewsHeaderItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
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