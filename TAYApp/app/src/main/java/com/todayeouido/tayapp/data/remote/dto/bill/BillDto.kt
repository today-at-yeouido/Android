package com.todayeouido.tayapp.data.remote.dto.bill

data class BillDto(
    val billName: String,
    val billType: Int,
    val committeeInfo: List<CommitteeDto?>,
    val id: Int,
    val isReflect : Boolean,
    val proposeDt: String,
    val proposer: String,
    val status: String,
    val views: Int,
    val emoji: String = ""
)

