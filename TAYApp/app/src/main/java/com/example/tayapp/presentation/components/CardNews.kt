package com.example.tayapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayEmoji
import com.example.tayapp.presentation.utils.TayIcons
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
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

@Composable
private fun CardNewsItem(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .size(MainActivity.displayWidth - KeyLine.twice(), 280.dp)
            .background(color = lm_card_yellow, shape = CardNewsShape.large)
    ) {
        Spacer(Modifier.height(45.dp))
        NewsTopLayout(modifier)
        Spacer(Modifier.height(17.dp))
        NewsBottomLayout()
    }
}

@Composable
private fun NewsTopLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(118.dp)
            .padding(horizontal = 14.dp)
    ) {
        NewsTopContent()
    }
}

@Composable
private fun NewsBottomLayout(modifier: Modifier = Modifier) {
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
            fontSize = 12.textDp
        )
    }
}


@Composable
private fun NewsTopContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            NewsLabel()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "중대재해처벌법 개정안",
                color = lm_gray000,
                fontSize = 18.textDp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "국회 본회의 통과",
                color = lm_gray000,
                fontSize = 18.textDp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(7.dp))
            Text(
                "제적의원 2/3이 찬성, 압도적 찬성 속 통과",
                fontSize = 13.textDp,
                fontWeight = FontWeight.Normal,
                color = lm_gray050
            )
            Spacer(Modifier.height(7.dp))
        }
        Text(
            text = TayEmoji.card_emoji,
            fontSize = 72.textDp
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
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = "개정안", color = lm_gray000, fontWeight = FontWeight.Normal, fontSize = 12.textDp)
    }
}

@Composable
fun NewsLabelIcon2() {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .background(color = lm_gray000, shape = CardNewsShape.medium)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = "접수", color = lm_gray700, fontWeight = FontWeight.Normal, fontSize = 12.textDp)
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
            fontSize = 12.textDp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "중대재해처벌법, 두려움을 기회로 중대재해처벌법, 두려움을 기회로 중대재해처벌법, 두려움을 기회로",
            overflow = TextOverflow.Ellipsis,
            color = lm_gray050,
            maxLines = 1,
            fontSize = 13.textDp,
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