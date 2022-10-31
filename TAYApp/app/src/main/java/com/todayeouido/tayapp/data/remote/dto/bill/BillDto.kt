package com.todayeouido.tayapp.data.remote.dto.bill

import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillItemDto

data class BillDto(
    val billName: String,
    val billType: Int,
    val committeeInfo: List<CommitteeDto?>,
    val id: Int,
    val isReflect : Boolean,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int,
    val emoji: String = ""
)

fun BillDto.toScrapBill(): ScrapBillItemDto =
    ScrapBillItemDto(
        id = id,
        billType = billType,
        isReflect = isReflect,
        proposeDt = proposeDt,
        proposer = proposer,
        status = status,
        views = views,
    )

