package com.example.tayapp.presentation.components

import androidx.compose.foundation.BorderStroke
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
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.BookmarkButton
import com.example.tayapp.presentation.utils.NavigateNextButton

@Composable
fun CardSearch(
    title: String = "2023 순천만국제정원박람회 지원 및 사후활용 에 관한 특별법안",
    list: List<String> = listOf<String>("1", "2","3","4"),
){
    if(list.count()==1) CardBill()
    else CardSearchMultiple(title, list)
}



@Composable
fun CardSearchMultiple(
    title: String = "2023 순천만국제정원박람회 지원 및 사후활용 에 관한 특별법안",
    list: List<String> = listOf<String>("1", "2","3","4"),
){
    TayCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Card_Inner_Padding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 2
            )
            Text(
                text = "총 ${list.count()}건 ",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = lm_gray600,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            TayDivider(
                modifier = Modifier.padding(vertical = 6.dp)
            )
            list.forEach {
                LineSearchedBill(people = it)
            }

            if(list.count() > 3){
                TayButton(
                    onClick = { /*TODO*/ },
                    backgroundColor = lm_gray000,
                    contentColor = lm_gray800,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, TayAppTheme.colors.border)
                ) {
                    Text("더보기", style = TayAppTheme.typo.typography.button)
                }
            }
            BookmarkButton(
                modifier = Modifier.align(Alignment.End),
                isBookmarked = false,
                onClick = { /*TODO*/ },
            )
        }
    }
}



@Composable
fun LineSearchedBill(
    date: String = "2022.06.17",
    people: String = "박태출 등 10인",
    bill: String = "개정안",
    status: String = "접수"
){
    Surface(
        modifier = Modifier.height(34.dp),
        color = lm_gray050,
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
                text = date,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = lm_gray600
            )
            Text(
                text = people,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = lm_gray600,
                modifier = Modifier.weight(1f)
            )
            PillList(bill, status)
            NavigateNextButton()
        }
    }
}

@Preview
@Composable
private fun SearchBillPreview(){
    TayAppTheme {
        LineSearchedBill()
    }
}

@Preview
@Composable
private fun SearchCardPreview(){
    TayAppTheme {
        CardSearch()
    }
}

@Preview
@Composable
private fun SearchCardPreview2(){
    TayAppTheme {
        CardSearch(list = listOf("1"))
    }
}