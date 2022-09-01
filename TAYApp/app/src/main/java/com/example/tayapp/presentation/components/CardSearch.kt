package com.example.tayapp.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.BookmarkButton
import com.example.tayapp.presentation.utils.NavigateNextButton

@Composable
fun CardSearch(
    bill: ScrapBillDto,
    onBillSelected: (Int) -> Unit,
    keyword: String = ""
){

    TayCard(
        modifier = Modifier.fillMaxSize(),
        enable = true,
        onClick = { onBillSelected(bill.bills.first().id) }
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            CardBillDefault(
                modifier = Modifier
                    .padding(9.dp)
                    .weight(1f),
                title = bill.billName,
                bill = bill.bills.first().billType,
                status = bill.bills.first().status,
                date = bill.bills.first().proposeDt,
                people = bill.bills.first().proposer,
                keyword = keyword
            )
        }
    }
}



@Composable
fun CardMultiple(
    bill: ScrapBillDto,
    onLineClick:(Int) -> Unit,
    keyword: String
){

    TayCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Card_Inner_Padding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val startIndex = bill.billName.indexOf(keyword)
            if (keyword != "" && startIndex != -1) {

                Text(
                    text = buildAnnotatedString {

                        if (startIndex == 0) {
                            withStyle(style = SpanStyle(color = TayAppTheme.colors.primary)) {
                                append(keyword)
                            }
                            append(bill.billName.substring(keyword.length, bill.billName.length))
                        } else {
                            append(bill.billName.substring(0, startIndex))

                            withStyle(style = SpanStyle(color = TayAppTheme.colors.primary)) {
                                append(keyword)
                            }
                            append(bill.billName.substring(startIndex + keyword.length, bill.billName.length))
                        }
                    },
                    color = TayAppTheme.colors.bodyText,
                    modifier = Modifier
                        .height(54.dp),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    maxLines = 2
                )
            } else {
                Text(
                    text = bill.billName,
                    modifier = Modifier
                        .height(54.dp),
                    color = TayAppTheme.colors.bodyText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    maxLines = 2
                )
            }
            Text(
                text = "총 ${bill.count}건 ",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            TayDivider(
                modifier = Modifier.padding(vertical = 6.dp)
            )

            if(bill.count > 3){
                bill.bills.subList(0,3).forEach {
                    LineSearchedBill(bill = it, onLineClick = onLineClick)
                }

                TayButton(
                    onClick = {},
                    backgroundColor = TayAppTheme.colors.background,
                    contentColor = TayAppTheme.colors.headText,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, TayAppTheme.colors.border)
                ) {
                    Text("더보기", style = TayAppTheme.typo.typography.button)
                }

            }else{
                bill.bills.forEach {
                    LineSearchedBill(bill = it, onLineClick = onLineClick)
                }
            }

        }
    }
}



@Composable
fun LineSearchedBill(
    bill: ScrapBillItemDto,
    onLineClick: (Int) -> Unit

){
    Surface(
        modifier = Modifier.height(34.dp).clickable(onClick = {onLineClick(bill.id)}),
        color = TayAppTheme.colors.layer1,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .height(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = bill.proposeDt,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText
            )
            Text(
                text = bill.proposer,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText,
                modifier = Modifier.weight(1f)
            )
            PillList(bill.billType, bill.status)
            NavigateNextButton()
        }
    }
}

