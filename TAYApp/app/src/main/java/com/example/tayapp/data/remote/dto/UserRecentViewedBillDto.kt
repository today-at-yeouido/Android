package com.example.tayapp.data.remote.dto

data class UserRecentViewedBillDto(
    val billName: String,
    val billType: Int,
    val id: Int,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int
)