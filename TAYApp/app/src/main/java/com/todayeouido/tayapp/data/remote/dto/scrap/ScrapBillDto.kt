package com.todayeouido.tayapp.data.remote.dto.scrap

import com.todayeouido.tayapp.data.remote.dto.bill.BillDto

data class ScrapBillDto(
    val billName: String,
    val bills: List<ScrapBillItemDto>,
    val groupId: Int,
    val count: Int
)
