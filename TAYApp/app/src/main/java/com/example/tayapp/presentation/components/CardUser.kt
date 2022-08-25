package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.bill.RecommendBillDto
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.example.tayapp.presentation.ui.theme.KeyLine
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.utils.Emoij
import com.example.tayapp.presentation.utils.EmoijList

private object CardUserValue {
    val ItemHeight = 72.dp
    val ItemWidth = 242.dp
    val SpacerBetween = 10.dp
    val fontWidth = 50.dp
    val cardWidth = 270.dp
    val headerHeight = 40.dp
}

@Composable
fun CardsUser(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
    navigateToLogin: () -> Unit,
    recommendBill: List<RecommendBillDto>
) {
    Box {
        LazyRow(
            modifier = if (UserState.isLogin()) Modifier else Modifier.blur(10.dp),
            userScrollEnabled = UserState.isLogin(),
            contentPadding = PaddingValues(KeyLine),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(recommendBill){ it ->
                CardUser(onClick = onClick, recommendBillDto = it)
            }
        }
        if(!UserState.isLogin()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(TayAppTheme.colors.layer3, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "로그인이 필요한 서비스입니다!",
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                TayButton(
                    onClick = navigateToLogin,
                    modifier.size(ButtonMediumWidth, ButtonMediumHeight),
                    backgroundColor = TayAppTheme.colors.primary,
                    contentColor = TayAppTheme.colors.headText
                ) {
                    Text("로그인", fontSize = 15.sp)
                }
            }
        }
    }
}


@Composable
fun CardUser(
    onClick: (Int) -> Unit = {},
    recommendBillDto: RecommendBillDto
) {
    TayCard(
        modifier = Modifier
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
 * innerpadding 값 수정
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
        EmoijText(bill.billName)

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
fun EmoijText(
    title: String
) {
    var selectedEmoij = ""

    run{
        EmoijList.forEach{ it ->
            it.key.forEach { key ->
                if(title.contains(key)){
                    selectedEmoij = it.value
                    return@run
                }
            }
        }
    }

    Text(
        selectedEmoij,
        modifier = Modifier
            .width(CardUserValue.fontWidth),
        fontSize = 30.sp,
        color = TayAppTheme.colors.headText
    )
}
