package com.example.tayapp.domain.use_case

import com.example.tayapp.domain.use_case.recent.GetRecentSearchUseCase
import com.example.tayapp.domain.use_case.recent.SaveRecentSearchUseCase
import javax.inject.Inject

data class RecentSearchTermUseCase @Inject constructor(
    val getRecentSearchUseCase: GetRecentSearchUseCase,
    val saveRecentSearchUseCase: SaveRecentSearchUseCase,
)