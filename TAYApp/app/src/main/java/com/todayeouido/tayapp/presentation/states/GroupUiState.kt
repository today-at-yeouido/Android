package com.todayeouido.tayapp.presentation.states

import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillDto
import com.todayeouido.tayapp.data.remote.dto.scrap.ScrapBillItemDto

data class GroupUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val billName: String = "",
    val bills: List<ScrapBillItemDto> = emptyList()
)