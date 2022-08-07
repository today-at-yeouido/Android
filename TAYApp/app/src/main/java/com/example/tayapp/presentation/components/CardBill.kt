package com.example.tayapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.example.tayapp.presentation.ui.theme.TayAppTheme
import com.example.tayapp.presentation.ui.theme.lm_gray075
import com.example.tayapp.presentation.ui.theme.lm_gray600
import com.example.tayapp.presentation.utils.BookmarkButton


/**
 * 최근 발의 법안
 */
@Composable
fun CardBill(
    modifier: Modifier = Modifier,
    bill: BillDto
){
    TayCard(
        modifier = modifier.fillMaxWidth()
    ) {
        CardBillDefault(
            title = bill.billName,
            bill = bill.committee,
            status = bill.status,
            date = bill.proposeDt,
            people = bill.proposer,
        )
    }
}

@Composable
fun CardBillWithScrap(){
    TayCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            CardBillDefault(
                modifier = Modifier
                    .padding(9.dp)
                    .weight(1f)
            )
            BookmarkButton(
                isBookmarked = false,
                onClick = { /*TODO*/ },
            )
        }
    }
}

@Composable
fun CardBillWithEmoij(
    title: String = "2023 순천만국제정원박람회 지원 및 사후활용 에 관한 특별법안",
    bill: String = "제정안",
    status: String = "가결"
){
    TayCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(Card_Inner_Padding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PillList(bill, status)
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            CardEmoij()
        }
    }
}

@Composable
private fun CardBillDefault(
    modifier: Modifier = Modifier.padding(Card_Inner_Padding),
    title: String = "2023 순천만국제정원박람회 지원 및 사후활용 에 관한 특별법안",
    bill: String = "제정안",
    status: String = "가결",
    date: String = "2022.06.17",
    people: String = "박대출 등 10인"
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PillList(bill, status)
        Text(
            text = title,
            modifier = Modifier
                .height(54.dp),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            maxLines = 2
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = date,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = lm_gray600
            )
            Text(
                text = people,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = lm_gray600
            )
        }
    }
}

@Composable
fun CardEmoij(){
    Box(
        contentAlignment = Alignment.Center
    ) {

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(50.dp)
                .background(lm_gray075)
        )
        EmoijText()
    }
}


@Preview
@Composable
private fun CardPreview(){
    TayAppTheme() {
        //CardBill()
    }
}

@Preview
@Composable
private fun Card2Preview(){
    TayAppTheme() {
        CardBillWithScrap()
    }
}

@Preview
@Composable
private fun Card3Preview() {
    TayAppTheme() {
        CardBillWithEmoij()
    }
}