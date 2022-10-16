package com.todayeouido.tayapp.presentation.states


import com.todayeouido.tayapp.domain.model.DetailBill

data class BillDetailUiState (
    val isLoading: Boolean = false,
    val billDetail: DetailBill = DetailBill(),
    val error: String = ""
)

