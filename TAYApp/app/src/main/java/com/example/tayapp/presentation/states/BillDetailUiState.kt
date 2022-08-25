package com.example.tayapp.presentation.states


import com.example.tayapp.domain.model.DetailBill

data class BillDetailUiState (
    val isLoading: Boolean = false,
    val billDetail: DetailBill = DetailBill(),
    val error: String = ""
)

