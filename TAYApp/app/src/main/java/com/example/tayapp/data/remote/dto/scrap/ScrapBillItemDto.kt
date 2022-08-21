package com.example.tayapp.data.remote.dto.scrap


data class ScrapBillItemDto(
    val id: Int,
    val billType: Int,
    val isReflect : Boolean,
    val proposeDt: String,
    val proposer: String,
    val billName: String,
    val status: String,
)
