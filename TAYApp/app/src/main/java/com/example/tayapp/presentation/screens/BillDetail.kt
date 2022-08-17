package com.example.tayapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.bill.DetailBillDto
import com.example.tayapp.presentation.components.*
import com.example.tayapp.presentation.ui.theme.*
import com.example.tayapp.presentation.utils.TayIcons
import com.example.tayapp.presentation.viewmodels.DetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BillDetail(billId: Int, upPress: () -> Unit) {

    val viewModel = hiltViewModel<DetailViewModel>()
    val detailState = viewModel.detailState.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { BillProgressDetail() },
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetPeekHeight = 10.dp,
        backgroundColor = lm_gray000,
        sheetBackgroundColor = lm_gray050
    ) {
        Column {
            TayTopAppBarWithScrap(billId, upPress, viewModel::addScrap)
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                DetailHeader(onProgressClick = {
                    coroutineScope.launch {

                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }, bill = detailState.value.billDetail!!)

                Spacer(modifier = Modifier.size(16.dp))

                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = KeyLine,
                            vertical = 24.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CardPieGraph()
                    CardBillLine()
                    BillPointText(detailState.value.billDetail.summary)
                    BillRevisionText()
                    BillDetailNews()
                }
            }
        }
    }
}


@Composable
fun DetailHeader(bill: DetailBillDto, onProgressClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(lm_card_yellow, RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KeyLine),

            ) {
                Spacer(modifier = Modifier.height(20.dp))
                PillList(bill.billType, bill.status)

                Text(
                    text = bill.billName,
                    style = TayAppTheme.typo.typography.h1
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = bill.proposer,
                        fontSize = 12.sp,
                        color = lm_gray600,
                        fontWeight = FontWeight.Normal
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = TayIcons.visibility_outlined,
                            contentDescription = "null",
                            tint = lm_gray600,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "${bill.views}",
                            fontSize = 12.sp,
                            color = lm_gray600,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }

        CardCommittee("상임위")
        CardBillProgress(onProgressClick, bill)

    }
}

@Composable
private fun CardCommittee(
    committee: String? = null
) {
    TayCard(
        modifier = Modifier.padding(horizontal = KeyLine)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Card_Inner_Padding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "상임위원회",
                    fontSize = 16.sp,
                    color = lm_gray700,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = TayIcons.help,
                    contentDescription = "null",
                    tint = lm_sementic_blue2,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = if(committee.isNullOrBlank()) "미정" else committee,
                fontSize = 16.sp,
                color = lm_gray600,
                fontWeight = FontWeight.Normal
            )

        }

    }
}

@Composable
private fun CardBillProgress(
    onProgressClick: () -> Unit,
    bill: DetailBillDto
) {
    TayCard(
        modifier = Modifier.padding(horizontal = KeyLine),
        enable = false
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(Card_Inner_Padding),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "진행현황",
                        fontSize = 16.sp,
                        color = lm_gray700,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = TayIcons.help,
                        contentDescription = "null",
                        tint = lm_sementic_blue2,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(onClick = onProgressClick)
                    )
                    Text(
                        text = "D+452",
                        fontSize = 16.sp,
                        color = lm_gray600,
                        fontWeight = FontWeight.Normal
                    )
                }
                LazyRow {
                    item{
                        BillProgressItem("접수", bill.proposeDt)
                        BillArrow()
                        BillProgressItem("심사", bill.committeeInfo.firstOrNull()?.procDt)
                        BillArrow()
                        BillProgressItem("심의", bill.jurisdictionInfo.firstOrNull()?.procDt)
                        BillArrow()
                        BillProgressItem("가결", bill.plenaryInfo.firstOrNull()?.procDt)
                        BillArrow()
                        BillProgressItem("정부이송", bill.transferInfo.firstOrNull()?.transDt)
                        BillArrow()
                        BillProgressItem("공포", bill.announceInfo.firstOrNull()?.announceDt)
                    }
                }
            }

        }
    }
}

@Composable
private fun CardPieGraph() {
    TayCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 11.dp)
        ) {
            Text(
                text = "본 회의 투표 결과",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = lm_gray700,
                modifier = Modifier.padding(horizontal = 3.dp),
                maxLines = 1
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PieGraph()
            }
        }
    }
}

@Composable
private fun CardBillLine() {
    TayCard(
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 11.dp)
        ) {
            Text(
                text = "이 법에 접수된 의안이 또 있어요!",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = lm_gray700,
                modifier = Modifier.padding(horizontal = 3.dp),
                maxLines = 1
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LineSearchedBill()
                LineSearchedBill()
            }
            TayButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("버튼", style = TayAppTheme.typo.typography.button)
            }
        }
    }
}

@Composable
private fun BillPointText(
    summary: String? = ""
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Title(string = "법안 핵심 내용")
        Text(
            text = "제안이유 및 주요내용",
            style = TayAppTheme.typo.typography.body1
        )
        Text(
            text = if(summary.isNullOrBlank()) "" else summary,
            style = TayAppTheme.typo.typography.body2
        )
    }
}

@Composable
private fun BillDetailNews() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        NewsHeader()
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CardNews()
            CardNews()
            CardNews()
            CardNews()
            CardNews()
        }
    }
}

@Composable
private fun NewsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Title(string = "관련 뉴스")
        Spinner()
    }
}

/**
 * pill수정 필요
 */
@Composable
private fun BillRevisionText() {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Title(string = "개정 내용 확인하기")

        TayCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(Card_Inner_Padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Title(string = "개정 내용 확인하기")
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    BillRevisionItem()
                    BillRevisionItem()
                    BillRevisionItem()
                    BillRevisionItem()
                }
            }
        }

        TayButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("개정 내용 확인하기", style = TayAppTheme.typo.typography.button)
            Icon(
                imageVector = TayIcons.navigate_next,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun BillRevisionItem() {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Pill("개정안")
        Text(
            text = "어쩌구저꺼주",
            style = TayAppTheme.typo.typography.body2
        )
    }
}

@Composable
private fun BillProgressItem(
    string: String,
    date: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        if(date.isNullOrBlank()) DashPill(string)
        else {
            Pill(string)
            Text(
                text = "22.07.20",
                fontSize = 9.sp,
                color = lm_gray600,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun BillArrow() {
    Icon(
        imageVector = TayIcons.navigate_next,
        contentDescription = ""
    )
}

@Preview
@Composable
fun BillDetailPreview() {
    TayAppTheme {
        //BillDetail()
        //BillProgressItem()
        //CardBillProgress()
    }
}