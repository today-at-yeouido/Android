package com.example.tayapp.presentation.states

import com.example.tayapp.domain.use_case.BillTable

data class BillTableUiState(
    val isLoading: Boolean = false,
    val billTable: BillTable? = null,
    val error: String = ""
)
