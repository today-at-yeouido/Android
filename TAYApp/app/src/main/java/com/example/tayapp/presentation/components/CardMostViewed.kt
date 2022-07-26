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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayEmoji
import com.example.tayapp.presentation.utils.TayIcons
import com.google.accompanist.pager.*


private object MostViewedValues {
    val Section_Title_Padding = 7.dp
    val KeyLine = 11.dp
    val Card_Height = 280.dp
    val Padding = 14.dp
    val Card_Gap = 5.dp
    val Indicator_Gap = 10.dp
    val Indicator_Size = 6.dp
    val Card_Top_Padding = 40.dp
    val Card_Between_Height = 12.dp
    val Content_Height = 118.dp
    val News_Height = 110.dp

}

@Composable
fun CardMostViewed(items: List<Int>) {
    MostViewedTitle()
    MostViewedRow(items = items)
}

@Composable
private fun MostViewedTitle() {
    Text(
        "최근 이슈 법안", style = TayTypography.h3,
        modifier = Modifier.padding(
            horizontal = KeyLine,
            vertical = MostViewedValues.Section_Title_Padding
        )
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MostViewedRow(
    modifier: Modifier = Modifier,
    items: List<Int>
) {
    val pagerState = rememberPagerState()

    Box() {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .zIndex(0.5f)
                .align(Alignment.TopCenter)
                .padding(top = MostViewedValues.Padding),
            spacing = MostViewedValues.Indicator_Gap,
            indicatorHeight = MostViewedValues.Indicator_Size,
            indicatorWidth = MostViewedValues.Indicator_Size,
            indicatorShape = CircleShape,
            activeColor = lm_gray000,
        )
        HorizontalPager(
            count = items.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = MostViewedValues.KeyLine)
        ) {
            MostViewedRowCard()
        }
    }
}

@Composable
private fun MostViewedRowCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = MostViewedValues.Card_Gap)
            .size(MainActivity.displayWidth - KeyLine.twice(), MostViewedValues.Card_Height)
            .background(color = lm_card_yellow, shape = CardNewsShape.large)
    ) {
        Spacer(Modifier.height(MostViewedValues.Card_Top_Padding))
        CardContentLayout()
        Spacer(Modifier.height(MostViewedValues.Card_Between_Height))
        CardNewsLayout()
    }
}

@Composable
private fun CardContentLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(MostViewedValues.Content_Height)
            .padding(horizontal = MostViewedValues.Padding)
    ) {
        CardContent()
    }
}

@Composable
private fun CardNewsLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(MostViewedValues.News_Height)
            .background(color = lm_gray700, shape = CardNewsShape.large)
            .padding(MostViewedValues.Padding)
    ) {
        NewsSub(Modifier.padding(bottom = 5.dp))
        NewsHeaderItem()
        NewsHeaderItem()
    }
}

@Composable
private fun NewsSub(modifier: Modifier) {
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
private fun CardContent() {
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