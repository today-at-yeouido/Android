package com.example.tayapp.presentation.states


import com.example.tayapp.data.remote.dto.bill.DetailBillDto

data class BillDetailUiState (
    val isLoading: Boolean = false,
    val billDetail: DetailBillDto? = null,
    val error: String = ""
)

