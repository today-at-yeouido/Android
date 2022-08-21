package com.example.tayapp.presentation.states


import com.example.tayapp.data.remote.dto.scrap.ScrapBillDto

data class SearchState(
    val isLoading: Boolean = false,
    val searching: Boolean = false,
    val bill: List<ScrapBillDto> = emptyList(),
    val error: String = "",
    val recentTerm: String = "",
    val query: String = "",
    val keyword: String = ""
)
