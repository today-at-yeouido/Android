package com.example.tayapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.example.tayapp.presentation.components.LineSearchedBill
import com.example.tayapp.presentation.components.TayCard
import com.example.tayapp.presentation.components.TayTopAppBarWithBack
import com.example.tayapp.presentation.states.UserState
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons


//@Composable
//fun GroupBill(
//    upPress: () -> Unit = {},
//    onLineClick: (Int) -> Unit = {}
//){
//    GroupBill(upPress, onLineClick, )
//}



@Composable
fun GroupBill(
    upPress: () -> Unit = {},
    onLineClick: (Int) -> Unit = {},
    bill: List<ScrapBillItemDto>
){

    Column {
        TayTopAppBarWithBack(string = "입법현황 모두 보기", upPress = upPress)

        if (UserState.network) {
//            if (detailState.value.isLoading) {
//                LoadingView(modifier = Modifier.fillMaxSize())
//            } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    GroupBillHeader(title = "중대재해 처벌 등에 관한 법률", view = 123)
                    TayCard(
                        modifier = Modifier.padding(KeyLine)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Card_Inner_Padding, vertical = 18.dp)
                        ) {
                            Text(
                                text = "입법 현황",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = TayAppTheme.colors.bodyText,
                                modifier = Modifier.padding(bottom = 10.dp),
                                maxLines = 1
                            )

                            bill.forEach{ it ->
                                LineSearchedBill(bill = it, onLineClick = onLineClick)
                            }


                            Text(
                                text = "총 건",
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = TayAppTheme.colors.bodyText,
                                modifier = Modifier
                                    .align(Alignment.End),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
//            }
        } else {
            NetworkErrorScreen {
//                UserState.network = true
//                viewModel.tryGetBillDetail()
            }
        }
    }
}

@Composable
private fun GroupBillHeader(
    title: String,
    view: Int
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lm_gray700, RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
            .padding(start = KeyLine, end = KeyLine, top = 30.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            fontSize = 24.sp,
            color = TayAppTheme.colors.headText,
            text = title,
            style = TayAppTheme.typo.typography.h1
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = TayIcons.visibility_outlined,
                contentDescription = "null",
                tint = lm_gray600,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${view}",
                fontSize = 12.sp,
                color = lm_gray600,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}
