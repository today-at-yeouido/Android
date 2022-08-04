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
import androidx.compose.ui.unit.em
import androidx.compose.ui.zIndex
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.bill.MostViewedBillDto
import com.example.tayapp.data.remote.dto.bill.NewDto
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.components.MostViewedValues.Card_Gap
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayEmoji
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.utils.matchWidth
import com.example.tayapp.utils.textDp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


private object MostViewedValues {
    val Section_Title_Padding = 7.dp
    val KeyLine = 16.dp
    val Card_Height = 280.dp
    val Padding = 14.dp
    val Card_Gap = 10.dp
    val Indicator_Gap = 10.dp
    val Indicator_Size = 6.dp
    val Card_Top_Padding = 40.dp
    val Card_Between_Height = 12.dp
    val Content_Height = 118.dp
    val News_Height = 110.dp

}

@Composable
fun CardMostViewed(items: List<MostViewedBillDto>) {
    Title(
        "최근 이슈 법안",
        modifier = Modifier
            .padding(
                horizontal = KeyLine,
                vertical = 7.dp,
            )
    )
    MostViewedRow(items = items)
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MostViewedRow(
    items: List<MostViewedBillDto>
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
            itemSpacing = Card_Gap,
            contentPadding = PaddingValues(horizontal = MostViewedValues.KeyLine)
        ) { i ->
            MostViewedRowCard(bill = items[i])
        }
    }
}

@Composable
private fun MostViewedRowCard(bill: MostViewedBillDto) {
    Column(
        modifier = Modifier
            .size(MainActivity.displayWidth - KeyLine.twice(), MostViewedValues.Card_Height)
            .background(color = lm_card_yellow, shape = CardNewsShape.large),
    ) {
        Spacer(Modifier.height(MostViewedValues.Card_Top_Padding))
        CardContentLayout(bill = bill.billSummary)
        Spacer(Modifier.height(MostViewedValues.Card_Between_Height))
        CardNewsLayout(news = bill.news)
    }
}

@Composable
private fun CardContentLayout(bill: BillDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(MostViewedValues.Content_Height)
            .padding(horizontal = 14.matchWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardContent(bill)
    }
}

@Composable
private fun CardNewsLayout(news: List<NewDto>) {
    Column(
        modifier = Modifier
            .height(MostViewedValues.News_Height)
            .background(color = lm_gray700, shape = CardNewsShape.large)
            .padding(MostViewedValues.Padding)
    ) {
        NewsSub()
        NewsHeaderItem(news[0])
        NewsHeaderItem(news[1])
    }
}

@Composable
private fun NewsSub() {
    Row(modifier = Modifier.padding(bottom = 5.dp)) {
        Image(
            imageVector = TayIcons.card_article,
            contentDescription = "",
            modifier = Modifier
                .padding(end = 4.dp, bottom = 5.dp)
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
private fun CardContent(bill: BillDto) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            NewsLabel()
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                bill.billName,
                color = lm_gray000,
                fontSize = 18.textDp,
                lineHeight = 1.3.em,
                modifier = Modifier.width(190.matchWidth)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                bill.proposer,
                fontSize = 13.textDp,
                fontWeight = FontWeight.Normal,
                color = lm_gray050,
                lineHeight = 1.2.em
            )
            Spacer(Modifier.height(7.dp))
        }
        Spacer(modifier = Modifier.width(20.matchWidth))
        Text(
            text = TayEmoji.card_emoji,
            fontSize = 72.textDp,
            modifier = Modifier.requiredSize(80.dp)
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
fun NewsHeaderItem(news: NewDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text =  "${news.pubDate} ${news.newsFrom}",
            color = lm_gray200,
            fontSize = 12.textDp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.width(100.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = news.newsName,
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