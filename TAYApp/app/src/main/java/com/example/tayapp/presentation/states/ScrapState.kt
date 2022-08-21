package com.example.tayapp.presentation.states

import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.example.tayapp.domain.model.Bill

data class ScrapState(
    val isLoading: Boolean = false,
    val bill: List<ScrapBillDto> = emptyList(),
    val error: String = ""
)
