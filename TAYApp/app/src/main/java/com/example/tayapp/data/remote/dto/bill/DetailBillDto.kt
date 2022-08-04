package com.example.tayapp.data.remote.dto.bill

data class DetailBillDto(
    val billName: String,
    val billNum: String,
    val billType: Int,
    val committeeID: Int,
    val hwpurl: String,
    val pdfurl: String,
    val proposeDt: String,
    val proposer: String,
    val proposerKind: String,
    val status: String,
    val summary: String,
    val views: Int
)