package com.todayeouido.tayapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todayeouido.tayapp.data.remote.dto.bill.DetailBillDto
import com.todayeouido.tayapp.domain.model.BillProgressItem
import com.todayeouido.tayapp.presentation.ui.theme.Card_Inner_Padding
import com.todayeouido.tayapp.presentation.ui.theme.KeyLine
import com.todayeouido.tayapp.presentation.ui.theme.TayAppTheme
import com.todayeouido.tayapp.presentation.utils.TayIcons
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun CardBillProgress(
    onProgressClick: () -> Unit,
    billProgressItems: List<BillProgressItem>,
    proposeDt: String
) {
    TayCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = KeyLine),
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "진행현황",
                        fontSize = 16.sp,
                        color = TayAppTheme.colors.bodyText,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = TayIcons.help,
                        contentDescription = "null",
                        tint = TayAppTheme.colors.information2,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(onClick = onProgressClick)
                    )
                    Text(
                        text = "D+" + if (proposeDt.isNotBlank()) billDate(proposeDt).toString() else "",
                        fontSize = 16.sp,
                        color = TayAppTheme.colors.subduedText,
                        fontWeight = FontWeight.Normal
                    )
                }
                LazyRow() {
                    itemsIndexed(billProgressItems) { index, it ->
                        if (index != 0) BillArrow()
                        BillProgressItemComponent(string = it.title, it.date)
                    }
                }
            }

        }
    }
}


@Composable
private fun billDate(date: String): Int {

    val formatter = DateTimeFormatter.ISO_DATE
    val date2 = LocalDate.parse(date, formatter).atStartOfDay()
    val current = LocalDateTime.now()

    val diff = Duration.between(date2, current).toDays()
    return diff.toInt()
}

@Composable
private fun BillProgressItemComponent(
    string: String,
    date: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        if (date.isNullOrBlank()) DashPill(string)
        else {
            Pill(string, fontSize = 16.sp)
            Text(
                text = date.substring(2).replace("-", "."),
                fontSize = 9.sp,
                color = TayAppTheme.colors.subduedText,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun BillArrow() {
    Icon(
        imageVector = TayIcons.navigate_next,
        contentDescription = "",
        modifier = Modifier
            .width(12.dp)
            .padding(start = 2.dp, end = 2.dp, top = 5.dp)
    )
}

private fun getProgress(billDto: DetailBillDto): List<BillProgressItem> {
    val progressList: MutableList<BillProgressItem> =
        mutableListOf(BillProgressItem("발의", billDto.proposeDt))

    if (billDto.committeeInfo.firstOrNull()?.procResult == "회송") {
        progressList.add(BillProgressItem("심사", billDto.committeeInfo.firstOrNull()?.submitDt))
        progressList.add(BillProgressItem("회송", billDto.committeeInfo.firstOrNull()?.procDt))
    } else {
        when (billDto.status) {
            "철회", "본회의불부의" -> {
                progressList.add(
                    BillProgressItem(
                        "철회",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
            }
            "대안반영폐기" -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(
                    BillProgressItem(
                        "대안",
                        billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
            }
            "부결" -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(BillProgressItem("심의", billDto.plenaryInfo.firstOrNull()?.prsntDt))
                progressList.add(BillProgressItem("부결", billDto.plenaryInfo.firstOrNull()?.prsntDt))
            }
            "본회의의결" -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(BillProgressItem("가결", billDto.plenaryInfo.firstOrNull()?.prsntDt))
            }
            else -> {
                progressList.add(
                    BillProgressItem(
                        "심사",
                        if (!billDto.committeeInfo.firstOrNull()?.submitDt.isNullOrBlank())
                            billDto.committeeInfo.firstOrNull()?.submitDt
                        else
                            billDto.committeeInfo.firstOrNull()?.procDt
                    )
                )
                progressList.add(BillProgressItem("심의", billDto.plenaryInfo.firstOrNull()?.prsntDt))
                progressList.add(BillProgressItem("가결", billDto.plenaryInfo.firstOrNull()?.prsntDt))
                progressList.add(
                    BillProgressItem(
                        "정부이송",
                        billDto.transferInfo.firstOrNull()?.transDt
                    )
                )
                progressList.add(
                    BillProgressItem(
                        "공포",
                        billDto.announceInfo.firstOrNull()?.announceDt
                    )
                )
            }
        }
    }

    return progressList
}