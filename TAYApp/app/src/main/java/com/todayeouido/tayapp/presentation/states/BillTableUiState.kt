package com.todayeouido.tayapp.presentation.states

import com.todayeouido.tayapp.domain.use_case.BillTable

data class BillTableUiState(
    val isLoading: Boolean = false,
    val billTable: BillTable? = null,
    val error: String = ""
)
