package com.example.tayapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.tayapp.domain.model.MostViewedBill
import com.example.tayapp.domain.model.News
import com.example.tayapp.presentation.MainActivity
import com.example.tayapp.presentation.components.MostViewedValues.Card_Gap
import com.example.tayapp.presentation.states.FeedUiState
import com.example.tayapp.presentation.ui.theme.CardNewsShape
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_card_yellow
import com.example.tayapp.presentation.utils.StateColor
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
fun CardMostViewed(
    items: FeedUiState,
    onBillClick: (Int) -> Unit
) {
    Title(
        "최근 이슈 법안",
        modifier = Modifier
            .padding(
                horizontal = KeyLine,
                vertical = 7.dp,
            )
    )
    MostViewedRow(items = items.bill, onBillClick)

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MostViewedRow(
    items: List<MostViewedBill>,
    onBillClick: (Int) -> Unit
) {
    val pagerState = rememberPagerState()

    Box {
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
            activeColor = TayAppTheme.colors.background,
        )
        HorizontalPager(
            count = items.size,
            state = pagerState,
            itemSpacing = Card_Gap,
            contentPadding = PaddingValues(horizontal = MostViewedValues.KeyLine)
        ) { i ->
            MostViewedRowCard(billItem = items[i], onBillClick)
        }
    }
}

@Composable
private fun MostViewedRowCard(
    billItem: MostViewedBill,
    onBillClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .size(MainActivity.displayWidth - KeyLine.twice(), MostViewedValues.Card_Height)
            .background(
                color = StateColor(status = billItem.bill.status),
                shape = CardNewsShape.large
            ),
    ) {
        Spacer(Modifier.height(MostViewedValues.Card_Top_Padding))
        CardContentLayout(bill = billItem.bill, onBillClick)
        Spacer(Modifier.height(MostViewedValues.Card_Between_Height))
        CardNewsLayout(newsList = billItem.news)
    }
}

@Composable
private fun CardContentLayout(bill: MostViewedBill.Bill, onBillClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(MostViewedValues.Content_Height)
            .padding(horizontal = 14.matchWidth)
            .clickable { onBillClick(bill.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardContent(bill)
    }
}

@Composable
private fun CardNewsLayout(newsList: List<News>) {

    val mUriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(MostViewedValues.News_Height)
            .background(color = TayAppTheme.colors.bodyText, shape = CardNewsShape.large)
            .padding(MostViewedValues.Padding)
    ) {
        NewsSub()
        if (newsList.isEmpty()) {
            Text(
                "관련 뉴스가 없습니다.",
                modifier = Modifier.fillMaxSize().padding(top = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = TayAppTheme.colors.disableIcon
            )
        } else {
            for (news in newsList) {
                NewsHeaderItem(news, mUriHandler)
            }
        }
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
            colorFilter = ColorFilter.tint(TayAppTheme.colors.background)
        )
        Text(
            text = "관련 뉴스",
            fontWeight = FontWeight.Medium,
            color = TayAppTheme.colors.background,
            fontSize = 12.textDp
        )
    }
}


@Composable
private fun CardContent(bill: MostViewedBill.Bill) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            PillList(bill.billType, bill.status)
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                bill.billName,
                color = TayAppTheme.colors.background,
                fontSize = 18.textDp,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.3.em,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(190.matchWidth)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                bill.proposer,
                fontSize = 13.textDp,
                fontWeight = FontWeight.Normal,
                color = TayAppTheme.colors.layer1,
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
            .background(color = TayAppTheme.colors.bodyText, shape = CardNewsShape.medium)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = "개정안",
            color = TayAppTheme.colors.background,
            fontWeight = FontWeight.Normal,
            fontSize = 12.textDp
        )
    }
}

@Composable
fun NewsLabelIcon2() {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .background(color = TayAppTheme.colors.background, shape = CardNewsShape.medium)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = "접수",
            color = TayAppTheme.colors.bodyText,
            fontWeight = FontWeight.Normal,
            fontSize = 12.textDp
        )
    }
}


@Composable
fun NewsHeaderItem(news: News, mUriHandler: UriHandler) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clickable { mUriHandler.openUri(news.newsLink) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${news.pubDate}  ${news.newsFrom}",
            color = TayAppTheme.colors.border,
            fontSize = 12.textDp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.width(100.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = news.newsName.removeNewline(),
            overflow = TextOverflow.Ellipsis,
            color = TayAppTheme.colors.layer1,
            maxLines = 1,
            fontSize = 13.textDp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .width(210.dp)
                .padding(horizontal = 10.dp)
        )

        Icon(
            imageVector = TayIcons.navigate_next,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
        )
    }
}

private fun Dp.twice(): Dp = (2 * this.value).dp

// 개행문자 제거
private fun String.removeNewline(): String {
    return this.replace("\\r\\n|\\r|\\n|\\n\\r".toRegex(), " ")
}

// bill type 매핑
private fun Int.mapType(): String {
    return when (this) {
        0 -> "제정안"
        1 -> "개정안"
        2 -> "일부개정안"
        else -> "폐지안"
    }
}