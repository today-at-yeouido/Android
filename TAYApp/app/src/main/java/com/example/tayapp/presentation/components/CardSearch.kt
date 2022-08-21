package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    bill: Bill,
    onBillSelected: (Int) -> Unit,
    keyword: String = ""
){
    CardBill(bill = bill, onClick = onBillSelected, keyword = keyword)
}



@Composable
fun CardMultiple(
    bill: ScrapBillDto,
    onLineClick:(Int) -> Unit
){
    TayCard(
        modifier = Modifier.fillMaxWidth(),
        enable = true
    ) {
        Column(
            modifier = Modifier.padding(Card_Inner_Padding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = bill.billName,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 2,
                color = TayAppTheme.colors.bodyText
            )
            Text(
                text = "총 ${bill.bills.size}건 ",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TayAppTheme.colors.subduedText,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            TayDivider(
                modifier = Modifier.padding(vertical = 6.dp)
            )
            bill.bills.forEach {
                LineSearchedBill(bill = it, onLineClick = onLineClick)
            }

            if(bill.bills.size > 3){
                TayButton(
                    onClick = { /*TODO*/ },
                    backgroundColor = TayAppTheme.colors.background,
                    contentColor = TayAppTheme.colors.headText,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, TayAppTheme.colors.border)
                ) {
                    Text("더보기", style = TayAppTheme.typo.typography.button)
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

