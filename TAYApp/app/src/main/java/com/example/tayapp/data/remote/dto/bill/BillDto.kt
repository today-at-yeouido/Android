package com.example.tayapp.data.remote.dto.bill

data class BillDto(
    val billName: String,
    val billType: Int,
    val committee: String,
    val id: Int,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int
)

