package com.todayeouido.tayapp.domain.model

import com.todayeouido.tayapp.data.remote.dto.bill.BillDto

data class Bill(
    val billName: String,
    val billType: Int,
    val committeeInfo: List<CommitteeInfo?>,
    val id: Int,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int,
    val emoji: String
)


fun BillDto.toDomain(): Bill = Bill(
    billName = this.billName,
    billType = this.billType,
    committeeInfo =   listOf(CommitteeInfo(
        "a","b","c","d"
    )),
    proposeDt = this.proposeDt,
    id = this.id,
    proposer = this.proposer,
    status = this.status,
    views = this.views,
    emoji = this.emoji
)