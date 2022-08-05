package com.example.tayapp.presentation.states

import com.example.tayapp.data.remote.dto.bill.BillDto
import com.example.tayapp.data.remote.dto.bill.MostViewedBillDto

data class MostViewedUiState(
    val isLoading: Boolean = false,
    val bill: List<MostViewedBillDto> = emptyList(),
    val error: String = ""
)
