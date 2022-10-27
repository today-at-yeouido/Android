package com.todayeouido.tayapp.domain.model

import com.todayeouido.tayapp.data.remote.dto.bill.CommitteeDto

data class CommitteeInfo(
    val committee: String? = "",
    val procDt: String? = "",
    val procResult: String? = "",
    val submitDt: String? = ""
)

fun CommitteeDto.toDomain(): CommitteeInfo = CommitteeInfo(
    committee = committee,
    procDt = procDt,
    procResult = procResult,
    submitDt = submitDt
)