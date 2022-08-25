package com.example.tayapp.presentation.states

import com.example.tayapp.data.remote.dto.bill.RecommendBillDto
import com.example.tayapp.domain.model.MostViewedBill

data class FeedUiState(
    val isLoading: Boolean = false,
    val bill: List<MostViewedBill> = emptyList(),
    val recommendBill: List<RecommendBillDto> = emptyList(),
    val error: String = ""
)
