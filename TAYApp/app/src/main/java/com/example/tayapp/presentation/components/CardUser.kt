package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.Emoij

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
    modifier: Modifier = Modifier
){
    LazyRow(
        contentPadding = PaddingValues(KeyLine),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        item {
            CardUser()
            CardUser()
            CardUser()
        }
    }
}


@Composable
fun CardUser(){
    TayCard(
        modifier = Modifier
            .width(CardUserValue.cardWidth)
    ) {
        Column {
            CardUserHeader()
            CardUserItem()
            TayDivider()
            CardUserItem()
            TayDivider()
            CardUserItem()
        }
    }
}

@Composable
fun CardUserHeader(title: String = "과학"){
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(CardUserValue.headerHeight)
                .background(lm_gray075)
        )

        Text(
            "#$title",
            fontWeight = FontWeight.Medium,
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
    bill: String = "제정안",
    status: String = "가결",
    title: String = "가덕도 신공항 건설을 위한 특별법",
    onClick: () -> Unit = {}
){
    Row(
        modifier = Modifier
            .padding(Card_Inner_Padding)
            .height(CardUserValue.ItemHeight)
            .width(CardUserValue.ItemWidth)
            .clickable(onClick = onClick)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        EmoijText()

        Spacer(modifier = Modifier.width(CardUserValue.SpacerBetween))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PillList(bill, status)

            Text(
                text = title,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                maxLines = 2
            )
        }
    }
}


@Composable
fun EmoijText(
    emoij: String? = Emoij["해양"]
){
    Text(
        "$emoij",
        modifier = Modifier
            .width(CardUserValue.fontWidth),
        fontSize = 30.sp
    )
}
