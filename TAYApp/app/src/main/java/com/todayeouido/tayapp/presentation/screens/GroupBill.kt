package com.todayeouido.tayapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todayeouido.tayapp.data.remote.dto.bill.BillDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillItemDto
import com.todayeouido.tayapp.presentation.components.LineSearchedBill
import com.todayeouido.tayapp.presentation.components.TayCard
import com.todayeouido.tayapp.presentation.components.TayTopAppBarWithBack
import com.todayeouido.tayapp.presentation.states.GroupUiState
import com.todayeouido.tayapp.presentation.states.UserState
import com.todayeouido.tayapp.presentation.ui.theme.*
import com.todayeouido.tayapp.presentation.utils.TayIcons
import com.todayeouido.tayapp.presentation.viewmodels.DetailViewModel
import com.todayeouido.tayapp.presentation.viewmodels.GroupViewModel


@Composable
fun GroupBill(
    upPress: () -> Unit = {},
    onBillSelected: (Int) -> Unit = {}
){
    val viewModel = hiltViewModel<GroupViewModel>()
    val groupState by viewModel.groupState.collectAsState()

    GroupBill(upPress, onBillSelected, groupState.bills, groupState.billName)
}

@Composable
fun GroupBill(
    upPress: () -> Unit = {},
    onBillSelected: (Int) -> Unit = {},
    bill: List<ScrapBillItemDto>,
    billName: String
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
                    GroupBillHeader(title = billName, view = 123)
                    TayCard(
                        modifier = Modifier.padding(KeyLine)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Card_Inner_Padding, vertical = 18.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                LineSearchedBill(bill = it, onLineClick = onBillSelected)
                            }


                            Text(
                                text = "총 ${bill.size}건",
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
            color = lm_gray000,
            text = title,
            style = TayAppTheme.typo.typography.h1
        )
    }
}
