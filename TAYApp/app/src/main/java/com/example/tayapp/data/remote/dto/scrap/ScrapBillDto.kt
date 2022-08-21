package com.example.tayapp.data.remote.dto.scrap

import com.example.tayapp.data.remote.dto.bill.BillDto

data class ScrapBillDto(
    val billName: String,
    val bills: List<ScrapBillItemDto>
)
