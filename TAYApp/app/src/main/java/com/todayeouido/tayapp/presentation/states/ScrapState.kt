package com.todayeouido.tayapp.presentation.states

import com.todayeouido.tayapp.data.remote.dto.bill.BillDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.todayeouido.tayapp.domain.model.Bill

data class ScrapState(
    val isLoading: Boolean = false,
    val bill: List<ScrapBillDto> = emptyList(),
    val error: String = ""
)
