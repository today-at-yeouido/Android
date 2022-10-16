package com.todayeouido.tayapp.domain.model

import com.todayeouido.tayapp.data.remote.dto.bill.PlenaryInfoDto

data class PlenaryInfo(
    val conf: String? = "",
    val prsntDt: String? = "",
    val procDt: String? = "",
    val procResult: String? = "",
    val total: Int? = 0,
    val approval: Int? = 0,
    val opposition: Int? = 0,
    val abstention: Int? = 0,
)

fun PlenaryInfoDto.toDomain(): PlenaryInfo = PlenaryInfo(
    conf = conf,
    prsntDt = prsntDt,
    procDt = procDt,
    procResult = procResult,
    total = total,
    approval = approval,
    opposition = opposition,
    abstention = abstention
)