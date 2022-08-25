package com.example.tayapp.data.remote.dto.bill

data class RecommendBillDto(
    val committee: String,
    val billSummary: List<BillDto>
)
