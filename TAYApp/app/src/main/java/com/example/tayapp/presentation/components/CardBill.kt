package com.example.tayapp.presentation.components

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.domain.model.Bill
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.BookmarkButton
import com.example.tayapp.presentation.utils.Emoij


/**
 * 최근 발의 법안
 */
@Composable
fun CardBill(
    modifier: Modifier = Modifier,
    bill: Bill,
    onClick: (Int) -> Unit = {},
    keyword: String = ""
) {
    TayCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(bill.id) },
        enable = true
    ) {
        CardBillDefault(
            title = bill.billName,
            bill = bill.billType,
            status = bill.status,
            date = bill.proposeDt,
            people = bill.proposer,
            keyword = keyword
        )
    }
}

@Composable
fun CardBillWithScrap(
    bill: ScrapBillDto,
    _isBookMarked: Boolean = false,
    onBillSelected: (Int) -> Unit,
    onScrapClickNotClicked: () -> Unit = {},
    onScrapClickClicked: () -> Unit = {}
) {
    var isBookMarked = _isBookMarked

    TayCard(
        modifier = if(isBookMarked) Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp) else Modifier.size(0.dp),
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
                status = bill.bills.first().status,
                bill = bill.bills.first().billType,
                date = bill.bills.first().proposeDt,
                people = bill.bills.first().proposer
            )
            BookmarkButton(
                isBookmarked = isBookMarked,
                onClick = {
                    if(isBookMarked) onScrapClickClicked() else onScrapClickNotClicked()
                }
            )
        }
    }
}

@Composable
fun CardBillWithEmoij(
    bill: Bill,
    keyword: String = "",
    onClick: (Int) -> Unit = {}
) {
    TayCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(1234) },
        enable = true
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
                PillList(bill.billType, bill.status)
                Text(
                    text = bill.billName,
                    fontWeight = FontWeight.Medium,
                    color = TayAppTheme.colors.bodyText,
                    fontSize = 16.sp
                )
            }
            var selectedEmoij = ""
            Emoij.forEach{ it ->
                if(bill.billName.contains(it.key)) selectedEmoij = it.value
            }

            EmoijText(selectedEmoij)
        }
    }
}

@Composable
fun CardBillDefault(
    modifier: Modifier = Modifier.padding(Card_Inner_Padding),
    title: String = "2023 순천만국제정원박람회 지원 및 사후활용 에 관한 특별법안",
    bill: Int = 1,
    status: String = "가결",
    date: String = "2022.06.17",
    people: String = "박대출 등 10인",
    keyword: String = ""
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PillList(bill, status)
        val startIndex = title.indexOf(keyword)
        if (keyword != "" && startIndex != -1) {

            Text(
                text = buildAnnotatedString {

                    if (startIndex == 0) {
                        withStyle(style = SpanStyle(color = TayAppTheme.colors.primary)) {
                            append(keyword)
                        }
                        append(title.substring(keyword.length, title.length))
                    } else {
                        append(title.substring(0, startIndex))

                        withStyle(style = SpanStyle(color = TayAppTheme.colors.primary)) {
                            append(keyword)
                        }
                        append(title.substring(startIndex + keyword.length, title.length))
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
                text = title,
                modifier = Modifier
                    .height(54.dp),
                color = TayAppTheme.colors.bodyText,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 2
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = date,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText
            )
            Text(
                text = people,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText
            )
        }
    }
}

@Composable
fun CardEmoij(emoij: String ="") {
    Box(
        contentAlignment = Alignment.Center
    ) {

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(50.dp)
                .background(TayAppTheme.colors.layer2)
        )
        EmoijText(emoij)
    }
}

