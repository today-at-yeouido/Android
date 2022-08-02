package com.example.tayapp.presentation.states

import com.example.tayapp.data.remote.dto.BillDto

data class MostViewedUiState(
    val isLoading: Boolean = false,
    val bill: List<BillDto> = emptyList(),
    val error: String = ""
)
