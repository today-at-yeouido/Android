package com.example.tayapp.data.remote.dto

data class BillDto(
    val billName: String,
    val billType: Int,
    val id: Int,
    // 제안 날짜
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int
)

