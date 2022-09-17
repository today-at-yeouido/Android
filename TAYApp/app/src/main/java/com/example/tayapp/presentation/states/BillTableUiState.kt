package com.example.tayapp.presentation.states

import com.example.tayapp.domain.use_case.Article

data class BillTableUiState(
    val isLoading: Boolean = false,
    val billTable: List<Article>? = emptyList(),
    val error: String = ""
)
