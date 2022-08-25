package com.example.tayapp.data.remote.dto.bill

data class MostViewedBillDto(
    val billSummary: BillDto,
    val news: List<NewsDto>
)