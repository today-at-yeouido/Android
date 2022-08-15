package com.example.tayapp.domain.model

import com.example.tayapp.data.remote.dto.bill.BillDto

data class Bill(
    val billName: String,
    val billType: Int,
    val committeeInfo: List<Committee?>,
    val id: Int,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int
)
data class Committee(
    val committee: String,
    val procDt: String,
    val procResult: String,
    val submitDt: String
)

fun BillDto.toDomain(): Bill = Bill(
    billName = this.billName,
    billType = this.billType,
    committeeInfo =   listOf(Committee(
        "a","b","c","d"
    )),
    proposeDt = this.proposeDt,
    id = this.id,
    proposer = this.proposer,
    status = this.status,
    views = this.views
)