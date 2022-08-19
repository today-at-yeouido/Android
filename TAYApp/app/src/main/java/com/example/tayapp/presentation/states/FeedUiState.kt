package com.example.tayapp.presentation.states

import com.example.tayapp.domain.model.MostViewedBill

data class FeedUiState(
    val isLoading: Boolean = false,
    val bill: List<MostViewedBill> = emptyList(),
    val error: String = ""
)
