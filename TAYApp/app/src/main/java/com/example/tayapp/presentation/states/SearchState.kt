package com.example.tayapp.presentation.states

import com.example.tayapp.data.remote.dto.bill.BillDto

data class SearchState(
    val isLoading: Boolean = false,
    val searching: Boolean = false,
    val bill: List<BillDto> = emptyList(),
    val error: String = "",
    val recentTerm: String = ""
)
