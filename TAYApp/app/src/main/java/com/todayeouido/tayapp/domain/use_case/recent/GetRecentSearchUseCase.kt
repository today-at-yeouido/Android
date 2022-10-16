package com.todayeouido.tayapp.domain.use_case.recent

import com.todayeouido.tayapp.domain.repository.RecentSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecentSearchUseCase @Inject constructor(private val repository: RecentSearchRepository) {
    operator fun invoke() = repository.recentSearchTerm
}