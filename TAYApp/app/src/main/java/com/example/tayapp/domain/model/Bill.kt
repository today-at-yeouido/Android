package com.example.tayapp.domain.model

import com.example.tayapp.data.remote.dto.bill.BillDto

data class Bill(
    val billName: String,
    val billType: Int,
    val committee: String,
    val id: Int,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int
)

fun BillDto.toDomain(): Bill = Bill(
    billName = this.billName,
    billType = this.billType,
    committee = this.committee,
    proposeDt = this.proposeDt,
    id = this.id,
    proposer = this.proposer,
    status = this.status,
    views = this.views
)