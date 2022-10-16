package com.todayeouido.tayapp.presentation.states

import com.todayeouido.tayapp.data.remote.dto.bill.RecommendBillDto
import com.todayeouido.tayapp.domain.model.MostViewedBill

data class FeedUiState(
    val isLoading: Boolean = false,
    val bill: List<MostViewedBill> = emptyList(),
    val recommendBill: List<RecommendBillDto> = emptyList(),
    val error: String = ""
)
