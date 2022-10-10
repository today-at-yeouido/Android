package com.example.tayapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.R
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.bill.RecommendBillDto
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.utils.ThemeConstants.LIGHT
import com.example.tayapp.utils.ThemeConstants.SYSTEM
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

private object CardUserValue {
    val ItemHeight = 72.dp
    val ItemWidth = 242.dp
    val SpacerBetween = 10.dp
    val fontWidth = 50.dp
    val cardWidth = 270.dp
    val headerHeight = 40.dp
}

/**
 * 사용자 추천 법안
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CardsUser(
    onClick: (Int) -> Unit = {},
    navigateToLogin: () -> Unit,
    navigateToFavorite: () -> Unit,
    recommendBill: List<RecommendBillDto>
) {
    val pagerState = rememberPagerState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box {

        /**
         * 사용자 맞춤 추천 법안 viewpager
         * contentPadding을 이용하여 카드 위치를 중앙에서 왼쪽으로 이동
         */
        HorizontalPager(
            count = recommendBill.size,
            state = pagerState,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(start = KeyLine,end = screenWidth - CardUserValue.cardWidth - KeyLine),
            userScrollEnabled = UserState.isLogin()
        ) { i ->
            CardUser(
                onClick = onClick,
                recommendBillDto = recommendBill[i],
                modifier = Modifier
            )

        }

        when {
            !UserState.isLogin() -> {
                CardUserDialog(navigateToLogin, "로그인이 필요한 서비스입니다!", "로그인")
            }
            UserState.isLogin() && recommendBill.isEmpty() -> {
                CardUserDialog(navigateToFavorite, "관심 소관위를 설정해주세요!", buttonText = "설정하러 가기")
            }
        }
    }
}

@Composable
private fun BoxScope.CardUserDialog(
    navigateToLogin: () -> Unit,
    explanation: String,
    buttonText: String
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)){
        /**
         * 사용자 추천 법안 블러 이미지
         * 사용시점 : 로그인 안했거나 관심 소관위가 없을 경우
         */
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id =
                if(UserState.mode == LIGHT || (!isSystemInDarkTheme()&& UserState.mode == SYSTEM)) R.drawable.feed_blur_light
                else R.drawable.feed_blur_dark
            ),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.Companion
                .align(Alignment.Center)
                .background(TayAppTheme.colors.layer3, RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = explanation,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            TayButton(
                onClick = navigateToLogin,
                Modifier.size(ButtonMediumWidth, ButtonMediumHeight),
                backgroundColor = TayAppTheme.colors.primary,
                contentColor = TayAppTheme.colors.headText
            ) {
                Text(text = buttonText, fontSize = 15.sp)
            }
        }
    }
}

/**
 * 사용자 추천 법안 카드
 */
@Composable
fun CardUser(
    onClick: (Int) -> Unit = {},
    recommendBillDto: RecommendBillDto,
    modifier: Modifier = Modifier
) {
    TayCard(
        modifier = modifier
            .width(CardUserValue.cardWidth)
    ) {
        Column {
            CardUserHeader(recommendBillDto.committee)
            CardUserItem(onClick = onClick, bill = recommendBillDto.billSummary[0])
            TayDivider()
            CardUserItem(onClick = onClick, bill = recommendBillDto.billSummary[1])
            TayDivider()
            CardUserItem(onClick = onClick, bill = recommendBillDto.billSummary[2])
        }
    }
}

@Composable
fun CardUserHeader(title: String = "과학") {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(CardUserValue.headerHeight)
                .background(TayAppTheme.colors.layer1)
        )

        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            color = TayAppTheme.colors.bodyText,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


/**
 * 사용자 추천법안에 사용되는 카드
 */
@Composable
fun CardUserItem(
    bill: BillDto,
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(Card_Inner_Padding)
            .height(CardUserValue.ItemHeight)
            .width(CardUserValue.ItemWidth)
            .clickable(onClick = { onClick(bill.id) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EmojiText(bill.emoji)

        Spacer(modifier = Modifier.width(CardUserValue.SpacerBetween))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            PillList(bill.billType, bill.status)

            Text(
                text = bill.billName,
                fontWeight = FontWeight.Normal,
                color = TayAppTheme.colors.headText,
                fontSize = 16.sp,
                maxLines = 2
            )
        }
    }
}


@Composable
fun EmojiText(
    emoji: String
) {
    Text(
        emoji,
        modifier = Modifier
            .width(CardUserValue.fontWidth),
        fontSize = 30.sp,
        color = TayAppTheme.colors.headText
    )
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}