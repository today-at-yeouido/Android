package com.todayeouido.tayapp.data.remote.dto.bill

data class PlenaryInfoDto(
    val conf: String?,
    val prsntDt: String?,
    val procDt: String?,
    val procResult: String?,
    val total: Int?,
    val approval: Int?,
    val opposition: Int?,
    val abstention: Int?,
)
