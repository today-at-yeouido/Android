package com.example.tayapp.data.remote.dto.bill

data class HomeCommitteeBillDto(
    val mostViewedBill : List<MostViewedBillDto>,
    val recentCreatedBill : List<BillDto>
)
